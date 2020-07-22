<template>
  <v-dialog v-model="openModal" max-width="800" persistent>
    <v-card class="detail-task-card-form">
      <v-form ref="form" v-model="valid" @submit.prevent="onSubmit" lazy-validation>
        <v-card-title class="headline grey lighten-2 card-detail py-2" primary-title>
          <v-chip
            v-if="taskState !== undefined"
            class="mr-5"
            :color="state[taskState].color"
          >{{ state[taskState].name }}</v-chip>
          <!-- <v-textarea
            v-model="title"
            class="mx-0 pb-5"
            label="comments.."
            rows="1"
            solo
            auto-grow
            flat
            hide-details
          ></v-textarea>-->
          <input v-model="title" />
          <v-spacer></v-spacer>
          <!-- <v-btn icon @click.prevent="onStar">
            <v-icon color="amber darken-1">{{ active ? "mdi-star" : "mdi-star-outline" }}</v-icon>
          </v-btn>-->
          <v-btn color="grey darken-1" icon @click="onClose">
            <v-icon color="grey darken-1">mdi-close</v-icon>
          </v-btn>
        </v-card-title>
        <v-card-text class="py-0 px-3">
          <!-- <v-container> -->
          <v-row style="max-height:750px" class="overflow-y-auto">
            <v-col cols="8" class="formFieldCol">
              <v-row class="px-4 pb-4">
                <v-col cols="12" class="pb-3">
                  <v-radio-group
                    v-model="usePublic"
                    required
                    row
                    :rules="privateRules"
                    hide-details
                  >
                    <span class="additional-title">
                      <v-icon small>mdi-plus</v-icon>PRIVATE
                    </span>
                    <v-radio label="Public" value="true" class="mr-5"></v-radio>
                    <v-radio label="Private" value="false"></v-radio>
                    <v-spacer></v-spacer>
                  </v-radio-group>
                </v-col>
                <v-col cols="12" v-if="managers.length" class="py-3">
                  <span class="additional-title">
                    <v-icon small>mdi-account-outline</v-icon>MANAGERS
                  </span>
                  <div v-if="managers.length" class="pl-4" style="display:inline-block">
                    <v-tooltip v-for="member in managers" :key="member.userId" bottom>
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
                  </div>
                </v-col>
                <v-col cols="12" class="py-3" v-if="endDate || startDate">
                  <span class="additional-title">
                    <v-icon small>mdi-clock-outline</v-icon>DUE DATE
                  </span>
                  <v-icon>mdi-calendar-month-outline</v-icon>
                  {{ startDate }} ~ {{ endDate }}
                </v-col>
                <!-- <v-col cols="6" v-if="taskState !== undefined">
                  <p>STATE</p>
                  <v-chip filter :color="state[taskState].color">{{ state[taskState].name }}</v-chip>
                </v-col>-->
                <v-col class="py-3" cosl="12" v-if="taskLabel">
                  <span class="additional-title">
                    <v-icon small>mdi-label-outline</v-icon>LABEL
                  </span>
                  <v-chip
                    v-for="(label, i) in setLabel(taskLabel)"
                    :key="i"
                    :color="label.labelColor"
                    dark
                    class="ma-1"
                  >{{ label.labelName }}</v-chip>
                </v-col>
              </v-row>
              <v-row class="px-4 pb-4">
                <v-col cols="12" class="py-3">
                  <v-icon>mdi-text</v-icon>
                  <span>DESCRIPTION</span>
                  <v-textarea solo flat v-model="description" auto-grow rows="2"></v-textarea>
                </v-col>
                <v-col cols="12" v-if="existFiles">
                  <p>
                    <v-icon>mdi-paperclip</v-icon>ATTACHMENTS
                  </p>
                  <v-list>
                    <v-card
                      class="mx-3 mb-2"
                      outlined
                      v-for="(file,index) in existFiles"
                      :key="file.fileNo"
                    >
                      <v-list-item
                        class="px-2"
                        @click.prevent="downloadFile(index)"
                        style="z-index:97"
                      >
                        <v-list-item-avatar tile color="grey" size="60">
                          <v-icon x-large>mdi-file-outline</v-icon>
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title class="mb-1 headline">
                            {{ file.orgName }}
                            <span>({{ file.size }} bytes)</span>
                          </v-list-item-title>
                          <v-list-item-subtitle>{{ file.regDate }}</v-list-item-subtitle>
                        </v-list-item-content>
                        <v-list-item-action>
                          <v-btn icon @click.prevent="btnTest" style="z-index:9999 !important">
                            <v-icon color="grey lighten-1">mdi-close</v-icon>
                          </v-btn>
                        </v-list-item-action>
                      </v-list-item>
                      <!-- <v-card-actions>
                      <v-btn icon @click.prevent="btnTest">
                        <v-icon color="grey lighten-1">mdi-close</v-icon>
                      </v-btn>
                      </v-card-actions>-->
                    </v-card>
                  </v-list>
                </v-col>
                <v-col cols="12">
                  <v-file-input
                    chips
                    multiple
                    label="File input"
                    v-model="attachFiles"
                    hide-details
                  ></v-file-input>
                </v-col>
              </v-row>
              <!-- <v-divider></v-divider> -->
              <v-row class="pa-4 comment-row">
                <v-col cols="12">
                  <p>
                    <v-icon>mdi-forum</v-icon>ACTIVITY
                  </p>
                  <!-- <v-avatar size="32" color="grey">
                    <v-icon fab dark v-bind="attrs" v-on="on">mdi-plus</v-icon>
                  </v-avatar>-->
                  <v-textarea
                    v-model="comment"
                    class="mx-0 pb-5"
                    label="comments.."
                    rows="1"
                    solo
                    auto-grow
                    flat
                    outlined
                    hide-details
                  >
                    <template v-slot:append>
                      <v-btn depressed icon @click.prevent="sendComment" :disabled="invalidInput">
                        <v-icon color="blue" style="margin-bottom:10px;">mdi-send</v-icon>
                      </v-btn>
                    </template>
                    <template v-slot:prepend>
                      <v-avatar size="32" color="grey">
                        <template v-if="userInfo.imgCode">
                          <img :src="userInfo.imgCode" />
                        </template>
                        <template v-else>
                          <v-icon fab dark>mdi-plus</v-icon>
                        </template>
                      </v-avatar>
                    </template>
                  </v-textarea>

                  <template v-if="comments.length">
                    <v-slide-x-transition group>
                      <div class="my-1" v-for="comment in comments" :key="comment.commentNo">
                        <div class="comment-div" text color="info" max-width="95%">
                          <div class="comment-avatar">
                            <v-avatar size="32" color="grey" class="mr-2">
                              <template v-if="getMember(comment.memberNo).imgCode">
                                <img :src="getMember(comment.memberNo).imgCode" />
                              </template>
                              <template v-else>
                                <v-icon fab dark>mdi-plus</v-icon>
                              </template>
                            </v-avatar>
                          </div>
                          <p>
                            <span class="user-name">{{getMember(comment.memberNo).name}}</span>
                            <span class="reg-date">{{comment.regDate}}</span>
                          </p>
                          <div>
                            <div class="comment-balloon">{{comment.content}}</div>
                            <span class="comment-btn" v-if="comment.memberNo == project.memberNo">
                              <v-btn
                                x-small
                                icon
                                @click.prevent="editComment()"
                                color="grey lighten-1"
                              >
                                <v-icon>mdi-lead-pencil</v-icon>
                              </v-btn>
                              <v-btn
                                x-small
                                icon
                                @click.prevent="deleteComment()"
                                color="grey lighten-1"
                              >
                                <v-icon>mdi-trash-can</v-icon>
                              </v-btn>
                            </span>
                          </div>
                        </div>
                      </div>
                    </v-slide-x-transition>
                  </template>
                </v-col>
              </v-row>
            </v-col>
            <v-col cols="4" class="createTaskside pa-0">
              <v-subheader>ADD OPTIONS</v-subheader>
              <v-list-item>
                <v-btn block depressed @click.prevent="isOpenAddMember = true">
                  <v-icon left>mdi-account-plus</v-icon>MEMBERS
                </v-btn>
                <add-member
                  :openModal="isOpenAddMember"
                  @close="isOpenAddMember = false"
                  @addMember="addMember"
                  :existManagers="existManagers"
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
                          v-for="st in state"
                          :key="st.type"
                          filter
                          label
                          :color="st.color"
                        >{{ st.name }}</v-chip>
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
              <v-subheader>RESISTRANT</v-subheader>
              <v-list-item v-if="resistrant">
                <v-list-item-avatar v-if="resistrant.imgCode" size="32">
                  <!-- <pre>getMemberImg(taskInfo.task.memberNo)}}</pre> -->
                  <img :src="resistrant.imgCode" />
                </v-list-item-avatar>
                <v-list-item-avatar v-else size="32" class="user-avatars" color="grey">
                  <v-icon fab dark>mdi-account</v-icon>
                </v-list-item-avatar>
                <v-list-item-content>
                  <v-list-item-title v-html="resistrant.name"></v-list-item-title>
                  <v-list-item-subtitle v-html="getDate(this.taskInfo.task.regDate)"></v-list-item-subtitle>
                </v-list-item-content>
              </v-list-item>
            </v-col>
          </v-row>
          <!-- </v-container> -->
        </v-card-text>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text @click="onClose">Cancle</v-btn>
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
import AddMember from "./AddMember.vue";
export default {
  components: {
    LabelMenu,
    DateMenu,
    AddMember
  },
  computed: {
    ...mapState({
      project: "project",
      labelList: "labelList",
      userInfo: "userInfo",
      // taskSuperId: "taskSuperId",
      taskInfo: "taskInfo",
      memberList: "memberList"

      // user 멤버 no가져오기..
    }),
    invalidInput() {
      return !this.comment.trim();
    }
  },
  created() {
    console.log("## Task Card Detail ##", this.$route.params.tid);
    this.FETCH_TASK(this.$route.params.tid).then(() => {
      console.log("FETCH_TASK 완료~!");
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
        this.startDate = this.existStartDate = this.taskInfo.task.startDate;
      }
      if (this.taskInfo.task.endDate) {
        this.endDate = this.existEndDate = this.taskInfo.task.endDate;
      }
      if (this.taskInfo.task.managerString) {
        this.managers = this.existManagers = this.getManger(
          this.taskInfo.task.managerString
        );
      }
      this.resistrant = this.getMember(this.taskInfo.task.memberNo);
      this.description = this.taskInfo.task.description;
      this.comments = this.taskInfo.comments;

      console.log("created 끝~");
    });
    // console.log(this.taskInfo.task.labels);
  },
  data: () => ({
    active: false,
    isOpenAddMember: false,
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
    resistrant: undefined,
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
    existManagers: undefined,
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
    managers: [],
    members: [],
    comment: "",
    comments: []
  }),

  methods: {
    ...mapActions([
      "ADD_SUPER_TASK",
      "ADD_SUB_TASK",
      "FETCH_TASK",
      "ADD_COMMENT",
      "UPDATE_TASK",
      "DOWNLOAD_FILE"
    ]),
    ...mapMutations(["SET_ADD_TASK_MODAL", "SET_SUPER_TASK_ID"]),
    onSubmit() {
      // Create Project
      console.log("..UPDATE TASK..");
      // console.log("taskSuperId", this.taskSuperId);
      if (this.validate()) {
        let formData = new FormData();
        console.log("-----test-----");
        // formData.append("projectId", this.project.id);
        formData.append("title", this.title);
        formData.append("description", this.description);
        formData.append("usePublic", this.usePublic);
        // formData.append("memberNo", this.project.memberNo);
        formData.append("labels", this.labels);
        formData.append(
          "state",
          this.taskState ? this.state[this.taskState].type : "H"
        );
        if (this.attachFiles.length !== 0) {
          for (var i = 0, file; (file = this.attachFiles[i]); i++) {
            formData.append("attachFiles", file);
          }
        }
        // formData.append(
        //   "manager",
        //   this.managers.map(function(o) {
        //     return o.memberNo;
        //   })
        // );
        formData.append("startDate", this.startDate);
        formData.append("endDate", this.endDate);
        // formData.append("members", this.members);
        formData.append("taskId", this.$route.params.tid);
        var addMem = [];
        var subMem = [];
        if (this.taskInfo.task.managerString !== null) {
          addMem = this.managers
            .map(function(o) {
              return o.memberNo;
            })
            .filter(
              mem => !JSON.parse(this.taskInfo.task.managerString).includes(mem)
            );

          subMem = JSON.parse(this.taskInfo.task.managerString).filter(
            mem =>
              !this.managers
                .map(function(o) {
                  return o.memberNo;
                })
                .includes(mem)
          );
        }
        formData.append("addManager", addMem);
        formData.append("subManager", subMem);
        this.UPDATE_TASK(formData).then(() => {
          console.log("UPDATE TASK ~!!");
        });
        // this.ADD_SUB_TASK(formData).then(() => {
        //   console.log("------------------");
        //   console.log("ADD SUB  완료");
        //   //this.$router.push(`/projects/${data.id}`);
        // });
        // console.log("labels", this.labels);
        // console.log("state", this.taskState);
        // console.log("managers", this.managers);
        // console.log("this.title", this.title);
        // console.log("endDate", this.endDate);
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
      this.existStartDate = date;
      this.startDate = date;
    },
    addEndDate(date) {
      this.existEndDate = date;
      this.endDate = date;
    },
    getManger(managerString) {
      if (managerString !== null) {
        console.log("매니저있음!", managerString);
        var managerNos = JSON.parse(managerString);
        console.log("managerNos :", managerNos);
        console.log(this.memberList);
        const managers = [];
        for (var no in managerNos) {
          var mb = this.memberList.find(item => {
            return item.memberNo == managerNos[no];
          });
          managers.push(mb);
        }
        return managers;
      }
    },
    getMember(no) {
      var mb = this.memberList.find(item => {
        return item.memberNo == no;
      });
      return mb;
    },
    // getImgCode(item) {
    //   return "data:image;base64," + item.imgCode;
    // },
    addMember(members) {
      console.log("addMember");
      console.log(members);
      this.existManagers = members;
      this.managers = members;
    },
    getDate(date) {
      let temp = new Date(date);
      return (
        temp.toDateString() +
        "<br>" +
        temp.getHours() +
        " : " +
        (temp.getMinutes() >= 10
          ? temp.getMinutes() + ""
          : "0" + temp.getMinutes() >= 10)
      );
    },
    sendComment() {
      // console.log("send COmment..");
      if (this.invalidInput) return;
      let commentObj = {
        memberNo: this.project.memberNo,
        taskId: this.$route.params.tid,
        content: this.comment
      };
      // console.log(commentObj);
      this.ADD_COMMENT(commentObj).then(data => {
        console.log("addComment 완료~~!");
        console.log(this.taskInfo.comments);
        console.log(data);
        this.comments = this.taskInfo.comments;
        this.comment = "";
      });
    },
    downloadFile(index) {
      console.log("downloadFile실행..");
      console.log(this.existFiles[index]);
      this.DOWNLOAD_FILE(this.existFiles[index])
        .then(res => {
          console.log("downloadFile ㅎ액션~!");
          const url = window.URL.createObjectURL(new Blob([res.data])); // = window.URL.createObjectURL(new Blob([res.data], { type: 'application/zip' }));
          const link = document.createElement("a");
          link.href = url;
          link.setAttribute("download", this.existFiles[index].orgName);
          document.body.appendChild(link);
          link.click();
          // this.ids = [];
          alert("다운로드가 완료되었습니다.");
          // return res;
        })
        .catch(err => {
          console.log("err~!");
          console.error(err);
        });

      // this.DOWNLOAD_FILE(this.existFiles[index]);
      // .then(res => {
      //   const url = window.URL.createObjectURL(
      //     new Blob([res.data], { type: "image/png" })
      //   ); // = window.URL.createObjectURL(new Blob([res.data], { type: 'application/zip' }));
      //   const link = document.createElement("a");
      //   link.href = url;
      //   link.setAttribute("download");
      //   document.body.appendChild(link);
      //   link.click();
      //   // this.ids = [];
      //   alert("다운로드가 완료되었습니다.");
      // })
      // .catch(error => {
      //   alert("다운로드에 실패하였습니다.");
      //   this.error = error.data.error;
      //   // this.ids = [];
      // });

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
    },
    onStar() {
      this.active = !this.active;
    },
    editComment() {
      console.log("댓글 수정");
    },
    deleteComment() {
      console.log("댓글 삭제");
    }
  }
};
</script>

<style scoped>
span.additional-title {
  font-size: 14px;
  margin-bottom: 5px;
  margin-left: 5px;
  display: inline-block;
  width: 150px;
  /* font-weight: 600; */
  color: dimgrey;
}
.detail-task-card-form .col {
  padding: 0px;
}
/* .add-task-card-form .additional-cols {
  padding: 10px;
} */
.detail-task-card-form .formFieldCol {
  padding: 16px;
}
.detail-task-card-form input {
  height: 36px;
  padding-left: 10px;
  font-size: 20px;
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
/* .v-textarea.v-text-field--solo .v-input__prepend-inner,
.v-textarea.v-text-field--solo .v-input__prepend-outer,
.v-textarea.v-text-field--solo .v-input__append-inner,
.v-textarea.v-text-field--solo .v-input__append-outer {
  margin-top: 3px;
  margin-bottom: 3px;
} */
div.comment-row .v-text-field--filled > .v-input__control > .v-input__slot,
.v-text-field--full-width > .v-input__control > .v-input__slot,
.v-text-field--outlined > .v-input__control > .v-input__slot {
  min-height: 40px;
}
div.comment-row .v-input .v-label {
  height: 20px;
  line-height: 10px;
}
div.comment-div {
  display: inline-block;
  margin: 5px;
  max-width: 100%;
  margin-left: 0px;
}
div.comment-div p {
  display: inline-block;
  /* margin-left: 5px; */
  margin-bottom: 5px;
}
div.comment-div .user-name {
  font-weight: 400;
  font-size: 14px;
}
div.comment-div .reg-date {
  font-size: 12px;
  margin-left: 10px;
  color: #b1b1b1;
}
div.comment-balloon {
  max-width: 85%;
  padding: 15px;
  margin-left: 30px;
  background-color: #e0e5f1;
  border-radius: 20px;
  color: dimgrey;
  display: inline-block;
}
p.comment-btn {
  text-align: right;
  margin-right: 10px;
}
div.comment-avatar {
  margin-top: 10px;
  /* position: absolute; */
  display: inline-block;
}
</style>
