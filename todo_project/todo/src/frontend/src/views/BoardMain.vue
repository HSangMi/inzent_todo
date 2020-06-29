<template>
  <v-container fluid>
    <v-card>
      <v-card-title>TODAY TASKS</v-card-title>
      <v-divider></v-divider>
      <v-row>
        <v-col cols="12" md="8">
          <today-dash-board></today-dash-board>
        </v-col>
        <v-col cols="12" md="4">
          <today-chart></today-chart>
        </v-col>
      </v-row>
    </v-card>
    <br />
    <v-card stlye="margin:10px">
      <v-card-title>WEEK TASKS</v-card-title>
      <v-divider></v-divider>
      <v-row>
        <v-col cols="12" md="8">
          <week-dash-board></week-dash-board>
        </v-col>
        <v-col cols="12" md="4">
          <week-chart></week-chart>
        </v-col>
      </v-row>
    </v-card>
    <br />
    <v-card>
      <v-card-title>INTEREST TASKS</v-card-title>
      <v-divider></v-divider>
      <v-row>
        <v-col cols="12" md="8">
          <interest-dash-board></interest-dash-board>
        </v-col>
        <v-col cols="12" md="4">
          <interest-chart></interest-chart>
        </v-col>
      </v-row>
    </v-card>
  </v-container>
</template>

<script>
import TodayDashBoard from "../components/dashboard/TodayDashBoard.vue";
import TodayChart from "../components/dashboard/TodayChart.vue";
import WeekDashBoard from "../components/dashboard/WeekDashBoard.vue";
import WeekChart from "../components/dashboard/WeekChart.vue";
import InterestDashBoard from "../components/dashboard/InterestDashBoard.vue";
import InterestChart from "../components/dashboard/InterestChart.vue";

import { mapActions } from "vuex";

export default {
  components: {
    TodayDashBoard,
    TodayChart,
    WeekDashBoard,
    WeekChart,
    InterestDashBoard,
    InterestChart
  },
  async created() {
    await this.fetchDashBoard();
  },
  methods: {
    ...mapActions(["FETCH_TODAY_DASHBOARD"]), // 등록
    ...mapActions(["FETCH_WEEK_DASHBOARD"]),
    ...mapActions(["FETCH_INTEREST_DASHBOARD"]),
    fetchDashBoard() {
      //action : 비동기 통신 요청
      this.FETCH_TODAY_DASHBOARD(); // store -> actions
      this.FETCH_WEEK_DASHBOARD(); 
      this.FETCH_INTEREST_DASHBOARD(); 
    }
  }
};
</script>

<style scoped></style>
