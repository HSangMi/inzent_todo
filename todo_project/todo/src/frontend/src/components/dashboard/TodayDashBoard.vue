<template>
  <v-data-table
    :headers="headers"
    :items="tasks"
    :items-per-page="5"
    hide-default-header
    class="elevation-1"
  ></v-data-table>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      headers: [
        { text: "route", value: "prjTitle", align: "start", sortable: false },
        { text: "ctask", value: "cTitle" },
        { text: "regDate", value: "regDate" }
      ]
    };
  },
  computed: {
    ...mapState({
      tasks: "todayList"
    })
  }
};
</script>

<style></style>
