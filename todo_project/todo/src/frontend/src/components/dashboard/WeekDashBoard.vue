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
        { text: "url", value: "title", align: "start", sortable: false },
        { text: "task", value: "dueDate" },
      ],
    };
  },
  computed: {
    ...mapState({
      tasks: "weekList"
    })
  }
};
</script>

<style></style>
