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

export default {
  data() {
    return {};
  },
  components: {
    TodayDashBoard,
    WeekDashBoard,
    StarredDashBoard,
  },
  created() {},
  methods: {},
};
</script>

<style scoped></style>
