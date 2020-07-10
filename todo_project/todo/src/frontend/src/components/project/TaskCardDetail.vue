<template>
  <v-dialog v-model="openModal" max-width="800" persistent>
    <v-card class="add-task-card-form">
      <v-form ref="form" v-model="valid" @submit.prevent="onSubmit" lazy-validation>
        <v-card-title class="headline grey lighten-2" primary-title>
          <input v-model="title" />
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" icon @click="onClose">
            <v-icon color="black lighten-1">mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="py-0 px-3">
          <!-- <v-container> -->
          <v-row>
            <v-col cols="8" class="formFieldCol">
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
                <v-col cols="6" v-if="endDate || startDate">
                  <p>DATE</p>
                  <v-icon>mdi-calendar-month-outline</v-icon>
                  {{startDate}} ~ {{endDate}}
                </v-col>
                <v-col cols="6" v-if="taskState!==undefined">
                  <p>STATE</p>
                  <v-chip filter :color="state[taskState].color">
                    {{
                    state[taskState].name
                    }}
                  </v-chip>
                </v-col>
                <v-col cosl="6" v-if="taskLabel">
                  <p>LABEL</p>
                  <v-chip
                    v-for="(label, i) in setLabel(taskLabel)"
                    :key="i"
                    :color="label.labelColor"
                    dark
                    class="ma-1"
                  >{{ label.labelName }}</v-chip>
                </v-col>
              </v-row>
              <v-col cols="12">
                <p>
                  <v-icon>mdi-text</v-icon>DESCRIPTION
                </p>
                <v-textarea solo flat v-model="description" auto-grow></v-textarea>
              </v-col>
              <v-col cols="12" v-if="existFiles">
                <p>
                  <v-icon>mdi-paperclip</v-icon>ATTACHMENTS
                </p>
                <v-list>
                  <v-card
                    class="mx-3 mb-2"
                    outlined
                    v-for="file in existFiles"
                    :key="file.fileNo"
                    @click="test"
                  >
                    <v-list-item class="px-2">
                      <v-list-item-avatar tile color="grey" size="60">
                        <v-icon x-large>mdi-file-outline</v-icon>
                      </v-list-item-avatar>
                      <v-list-item-content>
                        <v-list-item-title class="mb-1 headline">
                          {{file.orgName}}
                          <span>({{file.size}} bytes)</span>
                        </v-list-item-title>
                        <v-list-item-subtitle>{{file.regDate}}</v-list-item-subtitle>
                      </v-list-item-content>
                      <v-list-item-action>
                        <v-btn icon @click="btnTest">
                          <v-icon color="grey lighten-1">mdi-close</v-icon>
                        </v-btn>
                      </v-list-item-action>
                    </v-list-item>
                  </v-card>
                </v-list>
              </v-col>
              <v-col cols="12">
                <v-file-input chips multiple label="File input" v-model="attachFiles"></v-file-input>
              </v-col>
              <v-divider></v-divider>
              <v-col cols="12">
                <p>
                  <v-icon>mdi-forum</v-icon>ACTIVITY
                </p>
              </v-col>
            </v-col>
            <v-col cols="4" class="createTaskside pa-0">
              <v-subheader>ADD OPTIONS</v-subheader>
              <v-list-item>
                <v-btn block depressed>
                  <v-icon left>mdi-account-plus</v-icon>MEMBERS
                </v-btn>
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
                          v-for="st in state"
                          :key="st.type"
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
                <label-menu
                  :pid="this.project.id"
                  @setLabel="updateLabel"
                  @addLabel="updateLabel"
                  :existLabel="existLabel"
                />
              </v-list-item>
              <v-list-item>
                <date-menu
                  @addStartDate="addStartDate"
                  @addEndDate="addEndDate"
                  :existStartDate="existStartDate"
                  :existEndDate="existEndDate"
                />
              </v-list-item>
              <v-subheader>ACTIONS</v-subheader>
              <v-list-item>
                <v-btn block depressed>
                  <v-icon left>mdi-eye</v-icon>WATCH
                </v-btn>
              </v-list-item>
              <v-list-item>
                <v-btn block depressed>
                  <v-icon left>mdi-archive-outline</v-icon>ARCHIVE
                </v-btn>
              </v-list-item>
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="onClose">Cancle</v-btn>
          <v-btn color="blue darken-1" text type="submit">SAVE</v-btn>
        </v-card-actions>
      </v-form>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";
import LabelMenu from "./LabelMenu.vue";
import DateMenu from "./DateMenu.vue";
export default {
  components: {
    LabelMenu,
    DateMenu
  },
  computed: {
    ...mapState({
      project: "project",
      labelList: "labelList",
      // taskSuperId: "taskSuperId",
      taskInfo: "taskInfo"

      // user 멤버 no가져오기..
    })
  },
  created() {
    console.log("업무자세히:", this.$route.params.tid);
    this.FETCH_TASK(this.$route.params.tid).then(() => {
      console.log("fetch task 완료~!");

      this.taskState = this.state.find(item => {
        return item.type == this.taskInfo.task.state;
      }).index;
      this.title = this.taskInfo.task.title;
      this.usePublic = this.taskInfo.task.usePublic + "";
      // console.log(this.taskInfo.task.labels);
      if (this.taskInfo.task.labels !== "") {
        this.existLabel = JSON.parse(this.taskInfo.task.labels);
        this.taskLabel = this.taskInfo.task.labels;
      }
      if (this.taskInfo.files.length) {
        this.existFiles = this.taskInfo.files;
      }
      if (this.taskInfo.task.startDate) {
        this.startDate = this.taskInfo.task.startDate;
      }
      if (this.taskInfo.task.endDate) {
        this.endDate = this.taskInfo.task.endDate;
      }
      this.description = this.taskInfo.task.description;
    });
    // console.log(this.taskInfo.task.labels);
  },
  data: () => ({
    openModal: true,
    clear: false,
    dialog: false,
    title: "",
    description: "",
    usePublic: undefined,
    attachFiles: [],
    startDate: "", //new Date().toISOString().substr(0, 10),
    endDate: "",
    dates: [],
    startDatePicker: false,
    endDatePicker: false,
    // taskState: undefined,
    taskState: undefined,
    state: [
      { index: 0, name: "보류", color: "defualt", type: "H" },
      { index: 1, name: "진행", color: "primary", type: "P" },
      { index: 2, name: "완료", color: "success", type: "C" },
      { index: 3, name: "긴급", color: "error", type: "E" },
      { index: 4, name: "대기", color: "warning", type: "W" }
    ],
    // taskStateName: ["보류", "진행", "완료", "긴급", "대기"],
    // taskStateColor: ["defualt", "primary", "success", "error", "warning"],
    taskLabel: undefined,
    labels: "",
    existLabel: undefined,
    existFiles: undefined,
    existStartDate: undefined,
    existEndDate: undefined,
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
    members: []
  }),

  methods: {
    ...mapActions(["ADD_SUPER_TASK", "ADD_SUB_TASK", "FETCH_TASK"]),
    ...mapMutations(["SET_ADD_TASK_MODAL", "SET_SUPER_TASK_ID"]),
    onSubmit() {
      // Create Project
      console.log("..ADD TASK..");
      // console.log("taskSuperId", this.taskSuperId);
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
          this.taskState ? this.state[this.taskState].type : "P"
        );
        if (this.attachFiles.length !== 0) {
          for (var i = 0, file; (file = this.attachFiles[i]); i++) {
            formData.append("attachFiles", file);
          }
        }
        formData.append("startDate", this.startDate);
        formData.append("endDate", this.endDate);
        formData.append("members", this.members);
        formData.append("taskId", this.$route.params.tid);
        // this.ADD_SUB_TASK(formData).then(() => {
        //   console.log("------------------");
        //   console.log("ADD SUB  완료");
        //   //this.$router.push(`/projects/${data.id}`);
        // });
        console.log("UPDATE TASK ~!!");
        /*           this.ADD_SUPER_TASK(formData).then(() => {
            console.log("------------------");
            console.log("ADD  SUPER 완료");
            //this.$router.push(`/projects/${data.id}`);
          }); */
        this.onClose();
      }
    },

    onClose() {
      this.$router.push(`/projects/${this.project.id}`);
      // this.openModal = false;
      // this.openModal = false;
      // this.SET_ADD_TASK_MODAL(false);
      // this.formClear();
      // this.$emit("close");
      // this.SET_SUPER_TASK_ID("");
    },
    remove(item) {
      const index = this.members.indexOf(item.name);
      if (index >= 0) this.members.splice(index, 1);
    },
    formClear() {
      this.$refs.form.reset();
      this.taskLabel = [];
      this.taskState = undefined;
      this.startDate = "";
      this.endDate = "";
      // this.clearLabel = true;
      // this.clearLabel = false;
    },
    validate() {
      return this.$refs.form.validate();
    },
    setLabel(labelString) {
      const labelArr = JSON.parse(labelString); // labelArr Json String으로 변환할것 -> DB에 저장하는값
      var label = []; // 라벨로 보여줄 객체뽑아올곳
      for (var i in labelArr) {
        var lb = this.labelList.find(item => {
          return item.labelNo == labelArr[i];
        });
        label.push(lb);
        //this.taskLabel[i] = this.labelList[labelArr[i]];
        //this.labels += this.taskLabel[i].labelNo + ":";
      }
      // console.log(label);
      return label;
    },
    updateLabel(labelArr) {
      // console.log("-0-0-0-0");
      // console.log(labelArr);
      this.labels = JSON.stringify(labelArr); // labelArr Json String으로 변환할것 -> DB에 저장하는값
      this.existLabel = labelArr;
      this.taskLabel = this.labels;
      // this.taskLabel = []; // 라벨로 보여줄 객체뽑아올곳
      // for (var i in labelArr) {
      //   var lb = this.labelList.find(item => {
      //     return item.labelNo == labelArr[i];
      //   });
      //   this.existLabel.push(lb);
      // }
    },
    addStartDate(date) {
      this.startDate = date;
    },
    addEndDate(date) {
      this.endDate = date;
    },
    test() {
      console.log("test실행");
      // console.log(
      //   "C:\\Users\\aa\\Desktop\\final-todo-workspace\\todo_project\\todo" +
      //     "/upload/files"
      // );
      // window.location.assign(
      //   "C:UsersaaDesktop\\final-todo-workspace\\todo_project\\todo\\upload\\files\\2020063003104822.png"
      // );
    },
    btnTest() {
      console.log("삭제버튼클릭");
    }
  }
};
</script>

<style>
.add-task-card-form .col {
  padding: 15px;
}
.add-task-card-form .formFieldCol {
  padding: 0px;
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
