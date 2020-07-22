<template>
  <v-dialog v-model="openModal" max-width="800" persistent>
    <v-card class="add-task-card-form">
      <v-form ref="form" v-model="valid" @submit.prevent="onSubmit" lazy-validation>
        <v-card-title class="headline grey lighten-2" primary-title>
          <h4 v-if="taskSuperId">CREATE SUB TASK</h4>
          <h4 v-else>CREATE SUPER TASK</h4>
          <v-spacer></v-spacer>
        </v-card-title>
        <v-card-text class="py-0 px-3">
          <!-- <v-container> -->
          <v-row>
            <v-col cols="8" class="formFieldCol">
              <v-col cols="12">
                <v-text-field
                  label="TASK TITLE*"
                  v-model="title"
                  :rules="titleRules"
                  required
                  auto-grow
                  outlined
                ></v-text-field>
              </v-col>
              <v-row class="px-5 pb-4">
                <v-col cols="6">
                  <p>PRIVATE*</p>
                  <v-radio-group v-model="usePublic" required row :rules="privateRules">
                    <br />
                    <v-radio label="Public" value="true"></v-radio>
                    <v-spacer></v-spacer>
                    <v-radio label="Private" value="false"></v-radio>
                    <v-spacer></v-spacer>
                  </v-radio-group>
                </v-col>
                <v-col v-if="managers.length">
                  <p>MANAGERS</p>
                  <v-tooltip v-for="member in managers" :key="member.id" bottom>
                    <template v-slot:activator="{ on, attrs }">
                      <v-avatar v-if="member.imgCode" size="32" class="user-avatars">
                        <img :src="member.imgCode" v-bind="attrs" v-on="on" />
                      </v-avatar>
                      <v-avatar v-else size="32" class="user-avatars" color="grey">
                        <v-icon fab dark v-bind="attrs" v-on="on">mdi-account</v-icon>
                      </v-avatar>
                    </template>
                    <span>{{ member.name }}</span>
                  </v-tooltip>
                  <span class="pl-4">{{managers.length}}명</span>
                </v-col>
                <v-col cols="6" v-if="endDate || startDate">
                  <p>DATE</p>
                  <v-icon>mdi-calendar-month-outline</v-icon>
                  {{startDate}} ~ {{endDate}}
                </v-col>
                <v-col cols="6" v-if="taskState >= 0">
                  <p>STATE</p>
                  <v-chip filter :color="state[taskState].color">
                    {{
                    state[taskState].name
                    }}
                  </v-chip>
                </v-col>
                <v-col cosl="6" v-if="taskLabel.length">
                  <p>LABEL</p>
                  <v-chip
                    v-for="(label, i) in taskLabel"
                    :key="i"
                    :color="label.labelColor"
                    dark
                    small
                    label
                    class="ma-1"
                  >{{ label.labelName }}</v-chip>
                </v-col>
              </v-row>
              <v-col cols="12">
                <v-textarea label="DESCRIPTION" v-model="description" auto-grow outlined></v-textarea>
              </v-col>
              <v-col cols="12">
                <v-file-input chips multiple label="File input" v-model="attachFiles"></v-file-input>
              </v-col>
            </v-col>
            <v-col cols="4" class="createTaskside pa-0">
              <v-subheader>ADD OPTIONS</v-subheader>
              <v-list-item>
                <v-btn block depressed @click.prevent="isOpenAddMember = true">
                  <v-icon left>mdi-account-plus</v-icon>managers
                </v-btn>
                <add-member
                  :openModal="isOpenAddMember"
                  @close="isOpenAddMember = false"
                  @addMember="addMember"
                />
              </v-list-item>
              <v-list-item>
                <v-menu offset-y :close-on-content-click="false">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn v-bind="attrs" v-on="on" block depressed>
                      <v-icon left>mdi-plus</v-icon>STATE
                    </v-btn>
                  </template>
                  <v-card class="my-chip-group">
                    <v-card-title>TASK STATE</v-card-title>
                    <v-card-text>
                      <v-chip-group v-model="taskState" column>
                        <v-chip
                          v-for="(st, i) in state"
                          :key="i"
                          filter
                          label
                          :color="st.color"
                        >{{st.name}}</v-chip>
                      </v-chip-group>
                    </v-card-text>
                  </v-card>
                </v-menu>
              </v-list-item>
              <v-list-item>
                <label-menu :pid="this.project.id" @addLabel="addLabel" />
              </v-list-item>
              <v-list-item>
                <date-menu @addStartDate="addStartDate" @addEndDate="addEndDate" />
              </v-list-item>
              <!-- <v-subheader>ACTIONS</v-subheader>
              <v-list-item>
                <v-btn block depressed>
                  <v-icon left>mdi-eye</v-icon>WATCH
                </v-btn>
              </v-list-item>
              <v-list-item>
                <v-btn block depressed>
                  <v-icon left>mdi-archive-outline</v-icon>ARCHIVE
                </v-btn>
              </v-list-item>-->
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="onClose">Cancle</v-btn>
          <v-btn color="blue darken-1" text type="submit">Create</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import LabelMenu from "./LabelMenu.vue";
import DateMenu from "./DateMenu.vue";
import AddMember from "./AddMember.vue";
export default {
  props: ["openModal"],
  components: {
    LabelMenu,
    DateMenu,
    AddMember
  },
  computed: {
    ...mapState({
      project: "project",
      labelList: "labelList",
      taskSuperId: "taskSuperId",
      lastSubSortNo: "lastSubSortNo"
      // user 멤버 no가져오기..
    })
    // getTaskLabel() {
    //   return this.taskLabel;
    // }
  },
  data: () => ({
    clear: false,
    dialog: false,
    isOpenAddMember: false,
    title: "",
    description: "",
    usePublic: "true",
    attachFiles: [],
    startDate: "", //new Date().toISOString().substr(0, 10),
    endDate: "",
    dates: [],
    startDatePicker: false,
    endDatePicker: false,
    // taskState: undefined,
    taskState: -1,
    state: [
      { name: "보류", color: "defualt", type: "H" },
      { name: "진행", color: "primary", type: "P" },
      { name: "완료", color: "success", type: "C" },
      { name: "긴급", color: "error", type: "E" },
      { name: "대기", color: "warning", type: "W" }
    ],
    // taskStateName: ["보류", "진행", "완료", "긴급", "대기"],
    // taskStateColor: ["defualt", "primary", "success", "error", "warning"],
    taskLabel: [],
    labels: "",
    imgRules: [
      value =>
        !value ||
        value.size < 2000000 ||
        "Avatar size should be less than 2 MB!"
    ],
    menu: false,
    titleRules: [
      v => !!v || "title is required",
      v => (v && v.length <= 100) || "title must be less than 100 characters"
    ],
    descRules: [
      v =>
        (v && v.length <= 100) || "description must be less than 100 characters"
    ],
    privateRules: [v => !!v || "private is required"],
    valid: true,
    managers: []
  }),

  methods: {
    ...mapActions(["ADD_SUPER_TASK", "ADD_SUB_TASK"]),
    ...mapMutations([
      "SET_ADD_TASK_MODAL",
      "SET_SUPER_TASK_ID",
      "SET_LAST_SUB_SORT_NO"
    ]),
    onSubmit() {
      // Create Project
      console.log("..ADD TASK..");
      console.log("taskSuperId", this.taskSuperId);
      if (this.validate()) {
        let formData = new FormData();
        console.log("-----test-----");
        formData.append("projectId", this.project.id);
        formData.append("title", this.title);
        formData.append("description", this.description);
        formData.append("usePublic", this.usePublic);
        formData.append("memberNo", this.project.memberNo);
        formData.append("labels", this.labels);
        formData.append(
          "state",
          this.taskState >= 0 ? this.state[this.taskState].type : "H"
        );
        if (this.attachFiles.length !== 0) {
          for (var i = 0, file; (file = this.attachFiles[i]); i++) {
            formData.append("attachFiles", file);
          }
        }
        formData.append("startDate", this.startDate);
        formData.append("endDate", this.endDate);

        formData.append(
          "manager",
          this.managers.map(function(o) {
            return o.memberNo;
          })
        );
        if (this.taskSuperId) {
          console.log("ADD SUB ~!!");
          console.log(this.lastSubSortNo);
          formData.append("taskSuperId", this.taskSuperId);

          formData.append("sortNo", this.lastSubSortNo);
          this.ADD_SUB_TASK(formData).then(() => {
            console.log("------------------");
            console.log("ADD SUB  완료");
            //this.$router.push(`/projects/${data.id}`);
          });
        } else {
          console.log("ADD SUPER ~!!");
          this.ADD_SUPER_TASK(formData).then(() => {
            console.log("------------------");
            console.log("ADD  SUPER 완료");
            //this.$router.push(`/projects/${data.id}`);
          });
        }
        this.onClose();
      }
    },

    onClose() {
      // this.openModal = false;
      this.formClear();
      this.SET_ADD_TASK_MODAL(false);
      // this.$emit("close");
      this.SET_SUPER_TASK_ID("");
      this.SET_LAST_SUB_SORT_NO(0);
    },
    remove(item) {
      const index = this.managers.indexOf(item.name);
      if (index >= 0) this.managers.splice(index, 1);
    },
    formClear() {
      //this.$refs.form.reset();
      this.title = "";
      this.description = "";
      this.taskLabel = [];
      this.taskState = undefined;
      this.startDate = "";
      this.endDate = "";
      this.labels = "";
      this.managers = [];
      this.usePublic = "true";
      this.attachFiles = [];
      this.$refs.form.resetValidation();
      // this.clearLabel = true;
      // this.clearLabel = false;
    },
    validate() {
      return this.$refs.form.validate();
    },
    addLabel(labelArr) {
      this.labels = JSON.stringify(labelArr); // labelArr Json String으로 변환할것 -> DB에 저장하는값
      this.taskLabel = []; // 라벨로 보여줄 객체뽑아올곳
      for (var i in labelArr) {
        var lb = this.labelList.find(item => {
          return item.labelNo == labelArr[i];
        });
        this.taskLabel.push(lb);
        //this.taskLabel[i] = this.labelList[labelArr[i]];
        //this.labels += this.taskLabel[i].labelNo + ":";
      }
      console.log("-----addLabel-------");
      // console.log(this.taskLabel);
      console.log(this.labels);
    },
    addStartDate(date) {
      this.startDate = date;
    },
    addEndDate(date) {
      this.endDate = date;
    },
    addMember(managers) {
      console.log("addMember");
      console.log(managers);
      this.managers = managers;
    }
    // getImgCode(item) {
    //   return "data:image;base64," + item.imgCode;
    // }
  }
};
</script>

<style>
.add-task-card-form .col {
  padding: 0px;
}
.add-task-card-form .formFieldCol {
  padding: 10px;
}
.v-input--radio-group.v-input--radio-group--row .v-radio {
  margin-right: 0px;
}
.v-radio .v-icon {
  margin: 0px;
  font-size: 32px;
}
.primary-icon .v-icon {
  color: #1976d2;
}
.secondary-icon .v-icon {
  color: #424242;
}
.success-icon .v-icon {
  color: #4caf50;
}
.info-icon .v-icon {
  color: #2196f3;
}
.warning-icon .v-icon {
  color: #fb8c00;
}
.error-icon .v-icon {
  color: #ff5252;
}
.createTaskside {
  background-color: #d2d2d2;
}
.v-expansion-panel-content__wrap {
  padding: 10px;
}
.my-chip-group .v-slide-group__content {
  display: grid;
  margin: 0px;
}
.my-chip-group .v-chip {
  margin: 4px;
}
</style>
