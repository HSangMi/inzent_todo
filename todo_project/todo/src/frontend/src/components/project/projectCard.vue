<template>
  <v-col cols="12" xs="6" sm="4" md="3" lg="2">
    <v-card class="mx-auto" width="250" min-heght="150">
      <router-link :to="`/projects/${project.id}`">
        <v-img
          class="white--text align-end"
          height="75px"
          src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
        >
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn icon>
              <v-icon>mdi-star</v-icon>
            </v-btn>
          </v-card-actions>

          <v-card-title>{{ project.title }}</v-card-title>
        </v-img>
        <v-progress-linear
          color="deep-purple accent-4"
          height="8"
          value="30"
        ></v-progress-linear>

        <v-card-subtitle class="pb-0"
          >{{ project.startDate }}~{{ project.endDate }}</v-card-subtitle
        >
        <v-card-text class="text--primary"
          >manager : {{ project.manager }}</v-card-text
        >
      </router-link>
    </v-card>
  </v-col>
</template>

<script>
export default {
  props: ["project"],
  data() {
    return {};
  },
  mounted() {
    console.log(this.project);
  },
};
</script>

<style></style>
