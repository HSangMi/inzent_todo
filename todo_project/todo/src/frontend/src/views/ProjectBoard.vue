<template>
  <v-container id="project" class="project-container" fluid tag="section">
    <v-app-bar color="project.coverColor || indigo darken-3" dense flat dark>
      <v-toolbar-title>프로젝트 타이틀</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-plus</v-icon>
      </v-btn>
      <v-btn icon @click="onStar">
        <v-icon>
          {{ active ? "mdi-star" : "mdi-star-outline" }}
        </v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </v-app-bar>
    <div class="flex-nowrap">
      <v-container class="list-container" fluid>
        <!-- <v-virtual-scroll width="100%"> -->
        <v-row class="ma-4 list-row">
          <div draggable="false">
            <Task-parent />
          </div>
          <div draggable="false">
            <Task-parent />
          </div>
          <div draggable="false">
            <Task-parent />
          </div>
          <div draggable="false">
            <Task-parent />
          </div>
          <div draggable="false">
            <Task-parent />
          </div>
          <div draggable="false">
            <Task-parent />
          </div>
        </v-row>
      </v-container>
      <!--       <draggable
        v-model="lists"
        class="row flex-nowrap"
        v-bind="getDragOptions"
      >
        <TaskList
          v-for="(listItem, index) in lists"
          :key="index"
          :board="getBoard"
          :list="listItem"
        ></TaskList>
      </draggable> -->
      <!-- </v-virtual-scroll> -->
    </div>
  </v-container>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
// import Draggable from "vuedraggable";

import TaskParent from "../components/project/TaskParent";
//
export default {
  components: {
    TaskParent,
    // Draggable,
    // TaskList,
  },
  data() {
    return {
      pid: 0,
      loading: false,
      selection: 1,
      active: false,
    };
  },
  computed: {
    ...mapState({
      project: "project",
    }),
  },
  methods: {
    ...mapMutations(["SET_HEADER_TITLE"]),
    ...mapActions(["FETCH_PROJECTS"]),
    reserve() {
      this.loading = true;

      setTimeout(() => (this.loading = false), 2000);
    },
    onStar() {
      this.active = !this.active;
    },
  },
  created() {
    console.log("---------------");
    console.dir(this.project);
  },
};
</script>

<style scoped>
.container {
  padding: 0px;
}
.flex-nowrap {
  position: relative;
}
.list-row {
  flex-wrap: nowrap !important;
}
.project-container {
  height: 100%;
}
.list-container {
  overflow-x: scroll;
  height: 100%;
}
</style>
