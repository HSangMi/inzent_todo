<template>
  <v-card class="mr-4 card-task-parent" width="375">
    <v-card-title>
      Task Title
      <v-spacer></v-spacer>
      <v-btn icon @click="show = !show">
        <v-icon>{{ show ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </v-card-title>
    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>

        <v-card-text>
          <v-row align="center" class="mx-0">
            <v-rating
              :value="4.5"
              color="amber"
              dense
              half-increments
              readonly
              size="14"
            ></v-rating>

            <div class="grey--text ml-4">4.5 (413)</div>
          </v-row>

          <div class="my-4 subtitle-1">
            $ • Italian, Cafe
          </div>

          <div>
            Small plates, salads & sandwiches - an intimate setting with 12
            indoor seats plus patio seating.
          </div>
          <v-chip-group
            v-model="selection"
            active-class="deep-purple accent-4 white--text"
            column
          >
            <v-chip>5:30PM</v-chip>
            <v-chip>7:30PM</v-chip>
            <v-chip>8:00PM</v-chip>
            <v-chip>9:00PM</v-chip>
          </v-chip-group>
        </v-card-text>
      </div>
    </v-expand-transition>

    <v-divider></v-divider>
    <v-card-text class="task-list">
      <Task-child />
      <Task-child />
      <Task-child />
      <Task-child />
    </v-card-text>
    <v-divider></v-divider>
    <v-card-actions>
      <v-btn text block><v-icon>mdi-plus</v-icon> Add Tasks..</v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      selection: 0,
      show: false,
      active: false,
    };
  },
  components: {
    TaskChild: () => import("./TaskChild.vue"),
  },
};
</script>

<style scoped>
.task-list {
  padding: 0px;
}
.card-task-parent {
  background-color: #dcdcdc;
}
.card-task-parent .v-card__title {
  font-size: 1.5em;
  font-weight: bold;
  color: #ffffff;
  background-color: #685083;
  padding: 10px;
}
</style>
