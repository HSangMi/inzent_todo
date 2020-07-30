<template>
  <v-container fluid tag="section">
    <v-row class="archive-row">
      <v-col cols="12" md="6">
        <sub-archive />
      </v-col>
      <v-col cols="12" md="6">
        <super-archive />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import SubArchive from "../components/archive/SubArchive.vue";
import SuperArchive from "../components/archive/SuperArchive.vue";
import { mapMutations } from "vuex";

export default {
  components: {
    SubArchive,
    SuperArchive,
  },
  created() {
    this.SET_HEADER_TITLE("보관함");
  },
  methods: {
    ...mapMutations(["SET_HEADER_TITLE"]),
  },
};
</script>
<style scoped>
.archive-row {
  margin-right: 80px;
  margin-left: 80px;
  margin-top: 30px;
}
</style>