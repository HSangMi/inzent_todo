<template>
  <v-card class="rounded-lg mx-auto my-3 card-task-child" width="360" flat>
    <v-card-title>
      Task title..
      <v-spacer></v-spacer>
      <v-btn icon>
        <v-icon>mdi-star-outline</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-subtitle class="pb-0">2020-03-18 ~ 2020-09-23</v-card-subtitle>
    <v-card-text>
      <v-row class="mx-0">
        <v-col cols="auto" class="pa-0">
          <v-chip-group
            v-model="selection"
            active-class="deep-purple accent-4 white--text"
            column
          >
            <v-chip small>front</v-chip>
            <v-chip small>design</v-chip>
          </v-chip-group>
        </v-col>
      </v-row>
      <v-row class="mx-0">
        <p class="my-auto">
          <v-icon size="20">mdi-paperclip</v-icon>-3
          <v-icon size="20">mdi-comment-text-outline</v-icon>-2
        </p>
        <v-spacer></v-spacer>
        <v-avatar color="indigo" size="36">
          <v-icon dark>mdi-account-circle</v-icon>
        </v-avatar>
      </v-row>
    </v-card-text>
  </v-card>
</template>

<script>
export default {
  data() {
    return {
      selection: 1,
    };
  },
};
</script>

<style>
.card-task-child {
  border: solid 0px #132343 !important;
  border-left-width: 15px !important;
  border-left-color: #eb7f7f !important;
}
.card-task-child .v-card__title {
  font-size: 1.3em;
  padding: 10px;
}
</style>
