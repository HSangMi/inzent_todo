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
            <v-row>
              <v-col cols="12" md="2">
                <calendar-filter></calendar-filter>
              </v-col>
              <v-col cols="12" md="10">
                <calendar></calendar>
              </v-col>
            </v-row>
          </v-card>
        </v-tab-item>
        <v-tab-item>
            <v-card flat>
              <v-divider></v-divider>
              <v-card-title>GANTT CHART</v-card-title>
              <v-divider></v-divider>
              <br/>
              <v-row>
                <v-col cols="12" md="2">
                  <gantt-filter></gantt-filter>
                </v-col>
                <v-col cols="12" md="10">
                  <gantt-chart></gantt-chart>
                </v-col>
              </v-row>
            </v-card>
        </v-tab-item>
      </v-tabs-items>
    </v-card>
  </v-container>
</template>

<script>
import Calendar from "../components/calendar/Calendar.vue";
import CalendarFilter from "../components/calendar/CalendarFilter.vue";
import GanttChart from "../components/ganttchart/GanttChart.vue";
import GanttFilter from "../components/ganttchart/GanttFilter.vue";

export default {
  components: {
    Calendar,
    CalendarFilter,
    GanttChart,
    GanttFilter
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