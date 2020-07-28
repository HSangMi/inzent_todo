<template>
  <v-card outlined height="730">
    <v-toolbar flat class="blue-grey lighten-5">
      <h4>상위업무 보관함</h4>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-divider></v-divider>
    <v-data-iterator
      :items="items"
      :items-per-page.sync="itemsPerPage"
      :page="page"
      :sort-by="sortBy.toLowerCase()"
      hide-default-footer
      no-data-text="없음"
      class="ma-2"
    >
      <template v-slot:default="props">
        <div class="mb-6">
          <v-card outlined v-for="item in props.items" :key="item.title" class="ma-4">
            <v-card-title class="subheading pa-2">{{ item.title }}</v-card-title>
            <v-divider></v-divider>
            <v-card-text class="archive-font mt-2 pa-1 mx-2">{{item.title}}</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small text @click="restoreSuper()">복구</v-btn>
              <v-btn small text @click="deleteSuper()">삭제</v-btn>
            </v-card-actions>
          </v-card>
        </div>
      </template>

      <template v-slot:footer>
        <v-row align="center" justify="center">
          <v-btn text x-small color="blue-grey" class="ma-2 white--text" @click="formerPage">
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <span class="ma-2 grey--text">Page {{ page }} of {{ numberOfPages }}</span>
          <v-btn text x-small color="blue-grey" class="ma-2 white--text" @click="nextPage">
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
  </v-card>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  data() {
    return {
      itemsPerPageArray: [4, 8, 12],
      search: "",
      filter: {},
      sortDesc: false,
      page: 1,
      itemsPerPage: 4,
      sortBy: "name",
      keys: ["Name", "Calories"],
      items: [],
    };
  },
  created() {
    this.fetchArchiveSuper();
  },
  computed: {
    ...mapState({ archiveSuper: "archiveSuper" }),
    numberOfPages() {
      return Math.ceil(this.items.length / this.itemsPerPage);
    },
    filteredKeys() {
      return this.keys.filter((key) => key !== `Name`);
    },
  },
  methods: {
    ...mapActions(["FETCH_ARCHIVE_SUPER"]),
    fetchArchiveSuper() {
      this.FETCH_ARCHIVE_SUPER().then(() => {
        console.log("슈퍼 아카이브", this.archiveSuper);
        this.items = this.archiveSuper;
      });
    },
    restoreSuper() {
      console.log("복구구구구");
    },
    deleteSuper() {
      console.log("삭제ㅔㅔㅔ");
    },
    nextPage() {
      if (this.page + 1 <= this.numberOfPages) this.page += 1;
    },
    formerPage() {
      if (this.page - 1 >= 1) this.page -= 1;
    },
    updateItemsPerPage(number) {
      this.itemsPerPage = number;
    },
  },
};
</script>

<style scoped>
.archive-font {
  font-size: 14px;
}
</style>