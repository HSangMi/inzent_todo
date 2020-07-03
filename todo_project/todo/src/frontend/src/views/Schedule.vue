<template>
  <v-container fluid>
    <v-card height="880" scrollable>
      <template>
        <v-tabs v-model="tabs" color="blue darken-4" centered>
          <v-tab>Calendar</v-tab>
          <v-tab>Gantt Chart</v-tab>
        </v-tabs>
      </template>

      <v-tabs-items v-model="tabs">
        <v-tab-item>
          <v-card flat>
            <v-divider></v-divider>
            <v-card-title>CALENDAR</v-card-title>
            <v-divider></v-divider>
            <br />
            <calendar></calendar>
          </v-card>
        </v-tab-item>
        <v-tab-item>
          <v-card flat>
            <v-divider></v-divider>
            <v-card-title>GANTT CHART</v-card-title>
            <v-divider></v-divider>
            <br />
           <gantt-chart></gantt-chart>
          </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </v-container>
</template>

<script>
import Calendar from "../components/schedule/Calendar.vue";
import GanttChart from "../components/schedule/GanttChart.vue";

export default {
  components: {
    Calendar,
    GanttChart
  },
  data() {
    return {
      tabs: null
    };
  }
};
</script>

<style>
</style>