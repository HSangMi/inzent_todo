<template>
  <v-dialog v-model="isAddCalendar" persistent max-width="700px">
    <v-stepper v-model="e1">
      <v-divider></v-divider>
      <v-stepper-header>
        <v-stepper-step :complete="e1 > 1" step="1">SELECT PROJECT/TASK</v-stepper-step>
        <v-divider></v-divider>
        <v-stepper-step :complete="e1 > 2" step="2">ADD TASK</v-stepper-step>
      </v-stepper-header>

      <v-stepper-items>
        <v-stepper-content step="1">
          <v-card-title class="text-h5">1. SELCT PROJECT</v-card-title>
          <!-- 프로젝트 선택 -->
          <v-select
            class="mb-6 mx-5"
            v-model="chkProject"
            @change="fetchChkProject()"
            :items="selectProjects"
            item-text="prjTitle"
            item-value="prjId"
            label="PROJECT"
            outlined
          ></v-select>
          <v-divider></v-divider>
          <v-card-title class="text-h5">2. SELECT TASK</v-card-title>
          <!-- 업무 대,소 인지 선택 -->
          <v-radio-group class="mb-6 mx-5" v-model="radios" :mandatory="false">
            <v-radio label="SELECT SUPER TASK" value="super"></v-radio>
            <v-select class="mb-5 mx-5" :items="selectSuperTasks.pTitle" label="SUPER TASK"></v-select>
            <v-radio class="mb-2" label="ADD SUPER TASK" value="sub"></v-radio>
            <!-- 소 선택시, 선택한 프로젝트의 업무 대 선택 -->
          </v-radio-group>
          <v-card-actions>
            <v-btn class="ma-2" color="primary" outlined small fab @click="e1 = 2">
              <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn text @click="SET_IS_ADD_CALENDAR(false)">CANCEL</v-btn>
          </v-card-actions>
        </v-stepper-content>

        <v-stepper-content step="2">
          <v-card class="mb-12" height="200px">
            <v-card-title class="text-h5">3. ADD TASK</v-card-title>
          </v-card>
          <v-card-actions>
            <v-btn class="ma-2" color="primary" outlined small fab @click="e1 = 1">
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <v-spacer></v-spacer>
            <v-btn color="primary">ADD</v-btn>
            <v-btn text @click="SET_IS_ADD_CALENDAR(false)">CANCEL</v-btn>
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
    //////////////
    e1: 1,
    radios: "super",
    chkProject: "",
    selectProjects: [],
    // projectsTitles: [], // 업무추가에서 선택할 프로젝트 아이템
    // projectsId:[],
    selectSuperTasks: []
  }),
  created() {
    this.FETCH_CALENDAR_LIST().then(() => {
      this.selectProjects = this.calendarList;
    });
  },
  computed: {
    ...mapState({
      calendarList: "calendarList"
    }),
    ...mapState({ getSuperTasks: "getSuperTasks" }),
    ...mapState(["isAddCalendar"])
  },
  methods: {
    ...mapMutations(["SET_IS_ADD_CALENDAR"]),
    ...mapActions(["FETCH_CALENDAR_LIST"]),
    ...mapActions(["FETCH_CALENDAR_SUPER_TASKS"]),

    fetchChkProject() {
      const projectData = {
        chkProject: this.chkProject
      };
      this.FETCH_CALENDAR_SUPER_TASKS(projectData).then(() => {
        this.selectSuperTasks = this.getSuperTasks;
        console.log(this.selectSuperTasks);
      });
    }
  }
};
</script>

<style>
</style>