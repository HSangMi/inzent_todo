<template>
  <v-dialog v-model="isAddCalendar" persistent max-width="700px">
    <v-stepper v-model="e1">
      <v-divider></v-divider>
      <v-stepper-header class="blue-grey lighten-5">
        <v-stepper-step :complete="e1 > 1" step="1">프로젝트 및 업무 선택</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 2" step="2">업무 등록</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card-title class="text-h5">프로젝트 선택</v-card-title>
          <!-- 프로젝트 선택 -->
          <v-alert v-model="alert" dense outlined type="error" class="mx-5 py-1" height="35px">
            <span style="font-size:13px">프로젝트를 선택해주세요.</span>
          </v-alert>
          <v-select
            class="mx-5"
            v-model="chkProject"
            @change="fetchChkProject()"
            :items="selectProjects"
            item-text="prjtitle"
            item-value="prjid"
            label="프로젝트"
            outlined
          ></v-select>

          <v-divider></v-divider>
          <v-card-title class="text-h5">업무 선택</v-card-title>
          <!-- 업무 선택 -->
          <v-radio-group class="mb-6 mx-5" v-model="radios" :mandatory="false">
            <!-- 업무 대 추가할 것인지 -->
            <v-radio class="mb-6" label="상위 업무 등록" value="addTask"></v-radio>
            <!-- 업무 대 선택 할것인지 -->
            <v-radio class="mb-2" label="하위 업무 등록" value="selectTask"></v-radio>
            <v-select
              class="mb-5 mx-5"
              v-model="chkSuperTask"
              v-if="radios == 'selectTask'"
              :items="selectSuperTasks"
              item-text="ptitle"
              item-value="pid"
              label="상위 업무 선택"
            ></v-select>
          </v-radio-group>
          <v-card-actions>
            <v-btn color="blue darken-1" text @click="SET_IS_ADD_CALENDAR(false)">취소</v-btn>
            <v-spacer></v-spacer>
            <v-btn class="ma-2" color="primary" outlined small fab @click="isChkPrj()">
              <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
          </v-card-actions>
        </v-stepper-content>

        <v-stepper-content step="2">
          <!-- 업무 추가 -->
          <v-card class="mb-12" height="200px">
            <v-card-title class="text-h5">업무 등록</v-card-title>
          </v-card>
          <v-card-actions>
            <v-btn class="ma-2" color="primary" outlined small fab @click="e1 = 1">
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="SET_IS_ADD_CALENDAR(false)">취소</v-btn>
            <v-btn color="primary" @click="addCalendarTask()">등록</v-btn>
          </v-card-actions>
          <!-- <v-btn class="ma-2" tile outlined color="primary" @click="e1 = 1">PREV</v-btn> -->
        </v-stepper-content>
      </v-stepper-items>
    </v-stepper>
  </v-dialog>
</template>

<script>
import { mapMutations, mapState, mapActions } from "vuex";

export default {
  data: () => ({
    e1: 1,
    radios: "addTask",
    chkProject: "",
    chkSuperTask: "",
    selectProjects: [],
    selectSuperTasks: [],
    alert: false,
  }),
  created() {
    this.FETCH_CALENDAR_PRJOECT().then(() => {
      this.selectProjects = this.getProjects;
    });
  },
  computed: {
    ...mapState({
      getProjects: "getProjects",
    }),
    ...mapState({ getSuperTasks: "getSuperTasks" }),
    ...mapState(["isAddCalendar"]),
  },
  methods: {
    ...mapMutations(["SET_IS_ADD_CALENDAR"]),
    ...mapActions(["FETCH_CALENDAR_PRJOECT"]),
    ...mapActions(["FETCH_CALENDAR_SUPER_TASKS"]),
    ...mapActions(["ADD_CALENDAR_SUPER_TASKS"]),
    ...mapActions(["ADD_CALENDAR_SUB_TASKS"]),

    // 선택한 프로젝트에 따라 업무 대 조회
    fetchChkProject() {
      this.alert = false;
      const projectData = {
        chkProject: this.chkProject,
      };
      this.FETCH_CALENDAR_SUPER_TASKS(projectData).then(() => {
        this.selectSuperTasks = this.getSuperTasks;
      });
    },
    // 업무 추가
    addCalendarTask() {
      if (this.radios == "addTask") {
        const addSupper = {
          chkProject: this.chkProject,
        };
        // 업무 대 추가
        this.ADD_CALENDAR_SUPER_TASKS(addSupper).then(() => {
          console.log("super 추가");
        });
      } else {
        const addSub = {
          chkProject: this.chkProject,
          chkSuperTask: this.chkSuperTask,
        };
        // 업무 소 추가
        this.ADD_CALENDAR_SUB_TASKS(addSub).then(() => {
          console.log("sub 추가");
        });
      }
    },
    isChkPrj() {
      console.log(this.chkProject);
      if (this.chkProject == "") {
        this.alert = true;
      } else {
        this.e1 = 2;
      }
    },
  },
};
</script>

<style></style>
