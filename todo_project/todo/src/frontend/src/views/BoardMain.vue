<template>
  <v-container fluid>
    <today-dash-board></today-dash-board>
    <br />
    <week-dash-board></week-dash-board>
    <br />
    <starred-dash-board></starred-dash-board>
  </v-container>
</template>

<script>
import TodayDashBoard from "../components/dashboard/TodayDashBoard.vue";
import WeekDashBoard from "../components/dashboard/WeekDashBoard.vue";
import StarredDashBoard from "../components/dashboard/StarredDashBoard.vue";
import { mapMutations } from "vuex"
export default {
  data() {
    return {};
  },
  components: {
    TodayDashBoard,
    WeekDashBoard,
    StarredDashBoard,
  },
  created() {
    this.SET_HEADER_TITLE("DASHBOARD");
  },
  methods: {
    ...mapMutations(["SET_HEADER_TITLE"]),
  },
};
</script>

<style scoped></style>
