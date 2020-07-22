<template>
  <div style="height:100%">
    <v-overlay :value="loading">
      <v-progress-circular indeterminate size="64"></v-progress-circular>
    </v-overlay>
    <v-container class="project-container" fluid tag="section">
      <v-app-bar color="#FFFFFF" dense flat absolute>
        <v-tabs color="grey">
          <v-tab>KANBAN BOARD</v-tab>
          <v-tab>GANTT CHART</v-tab>
        </v-tabs>
        <v-spacer></v-spacer>
        <v-btn icon>
          <v-icon>mdi-plus</v-icon>
        </v-btn>
        <v-btn icon @click="onStar">
          <v-icon>{{ active ? "mdi-star" : "mdi-star-outline" }}</v-icon>
        </v-btn>
        <v-btn icon color="grey lighten-1" @click.prevent="isOpenProjectInfo = true">
          <v-icon>mdi-information</v-icon>
        </v-btn>
      </v-app-bar>
      <v-divider></v-divider>
      <div class="ma-0 kanban-board-wrapper">
        <v-row class="mx-4 list-row">
          <draggable v-model="lists" v-bind="getDragOptions" class="row flex-nowrap">
            <Task-parent
              v-for="(listItem, index) in lists"
              :key="index"
              :board="getBoard"
              :list="listItem"
            />
            <v-card class="mr-4 pa-0 super-task-card" width="300">
              <v-card-actions>
                <v-btn text block @click.prevent="showAddTask('SUPER')">
                  <v-icon>mdi-plus</v-icon>Add Super Tasks..
                </v-btn>
              </v-card-actions>
            </v-card>
          </draggable>
          <!-- <add-super-task :openModal="addTaskModal" /> -->

          <add-super-task :openModal="addTaskModal" />
        </v-row>
      </div>
      <router-view :projectId="project.id"></router-view>
    </v-container>
    <project-info :openModal="isOpenProjectInfo" @close="isOpenProjectInfo = false" />
  </div>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import Draggable from "vuedraggable";

import TaskParent from "../components/project/TaskParent";
import AddSuperTask from "../components/project/AddTaskParent";
import ProjectInfo from "../components/project/ProjectInfo.vue";
// import AddSubTask from "../components/project/AddSubTask";
//
export default {
  components: {
    TaskParent,
    AddSuperTask,
    // AddSubTask,
    Draggable,
    ProjectInfo
    // TaskList,
  },
  data() {
    return {
      pid: 0,
      loading: false,
      selection: 1,
      active: false,
      isOpenProjectInfo: false
    };
  },
  created() {
    console.log("-------projectBoard--------");
    this.loading = true;
    console.log(this.$route.params.pid);
    this.fetchData().then(() => {
      console.log("SETHEADER : " + this.project.title);
      console.log("패치데이터 완료");
      this.loading = false;
      this.SET_HEADER_TITLE(this.project.title);
    });
  },
  computed: {
    ...mapState({
      project: "project",
      taskList: "taskList",
      addSubTaskModal: "addSubTaskModal",
      addTaskModal: "addTaskModal"
    }),
    getDragOptions() {
      return {
        animation: "200",
        ghostClass: "ghost",
        handle: ".task-list-header",
        group: "kanban-board-lists"
      };
    },
    getBoard() {
      console.log("getBoard 실행..");
      return this.project;
    },
    lists: {
      get() {
        return this.taskList;
      },
      async set(value) {
        console.log("lists.set 실행!");
        console.log(value);
        await this.reorderTaskLists({
          boardId: this.param,
          lists: value
        });
      }
    }
  },
  methods: {
    ...mapMutations([
      "SET_HEADER_TITLE",
      "SET_HEADER_TITLE",
      "SET_ADD_TASK_MODAL"
    ]),
    ...mapActions(["FETCH_PROJECT"]),
    fetchData() {
      return this.FETCH_PROJECT(this.$route.params.pid);
    },
    // reserve() {
    //   this.loading = true;

    //   //setTimeout(() => (this.loading = false), 2000);
    // },
    onStar() {
      this.active = !this.active;
    },
    reorderTaskLists() {
      console.log("reorderTaskList 실행..");
    },
    showAddTask(taskType) {
      // console.log("testestsetset");
      // console.log(sortNo);
      if (taskType === "SUPER") {
        console.log("SUPER TASK ADD..");
        this.SET_ADD_TASK_MODAL(true);
        // this.SET_SUPER_TASK_ID(taskId);
        // this.SET_LAST_SUB_SORT_NO(sortNo);
      }
    }
  }
};
</script>

<style scoped>
.project-container {
  /* padding: 100px 30px 30px 30px; */
  background: #eef2f9;
  min-height: 100%;
  padding: 0px;
  overflow-x: scroll;
  /* height: 100%; */
}
.kanban-board-wrapper {
  display: flex;
  /* align-items: flex-start; */
  flex-direction: column;
  padding-top: 50px;
}
.flex-nowrap {
  position: relative;
  height: 100%;
}
/* .list-row {
  overflow-x: scroll;
  height: 100%;
  flex-direction: column;
  flex-wrap: nowrap !important;
} */

.list-container {
  height: 100%;
  display: flex;
  /* align-items: flex-start; */
  flex-direction: column;
}

.super-task-card {
  background: #f6f8fc;
  height: fit-content;
  margin: 10px;
  padding: 10px;
  width: 335px;
  position: relative;
  border-radius: 5px;
  -webkit-box-shadow: 0px 0px 4px 0px rgba(134, 134, 162, 0.21);
  -moz-box-shadow: 0px 0px 4px 0px rgba(134, 134, 162, 0.21);
  box-shadow: 0px 0px 4px 0px rgba(134, 134, 162, 0.21);
}
.add-task-card-form .col {
  padding: 10px;
}
</style>
