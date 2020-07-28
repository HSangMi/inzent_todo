<template>
  <v-card outlined height="730">
    <v-toolbar flat class="blue-grey lighten-5">
      <h4>하위업무 보관함</h4>
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
      class="ma-5"
    >
      <template v-slot:default="props">
        <div class="mb-5">
          <v-row class="sub-row">
            <v-col v-for="item in props.items" :key="item.title" cols="12" sm="6" md="2" lg="6">
              <v-card outlined class="mb-5">
                <v-card-title class="subheading font-weight-bold">{{item.title}} > {{item.title}}</v-card-title>
                <v-card-text>{{item.title}}</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn small text @click.prevent="SET_IS_ARCHIVE_DIALOG(true)">복구</v-btn>
                  <v-btn small text @click="deleteSub()">삭제</v-btn>
                </v-card-actions>
              </v-card>
            </v-col>
          </v-row>
        </div>
      </template>

      <template v-slot:footer>
        <v-row align="center" justify="center">
          <v-btn text x-small color="blue-grey" class="ma-2 white--text" @click="formerPage">
            <v-icon>mdi-chevron-left</v-icon>
          </v-btn>
          <span class="ma-2 grey--text">page {{ page }} of {{ numberOfPages }}</span>
          <v-btn text x-small color="blue-grey" class="ma-2 white--text" @click="nextPage">
            <v-icon>mdi-chevron-right</v-icon>
          </v-btn>
        </v-row>
      </template>
    </v-data-iterator>
  </v-card>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";

export default {
  // components: {
  //   ArchiveDialog,
  // },
  data() {
    return {
      filter: {},
      sortDesc: false,
      page: 1,
      itemsPerPage: 6,
      sortBy: "name",
      keys: ["Name", "Calories"],
      items: [],
    };
  },
  created() {
    this.fetchArchiveSub();
  },
  computed: {
    ...mapState({ archiveSub: "archiveSub" }),
    numberOfPages() {
      return Math.ceil(this.items.length / this.itemsPerPage);
    },
    filteredKeys() {
      return this.keys.filter((key) => key !== `Name`);
    },
  },
  methods: {
    ...mapMutations(["SET_IS_ARCHIVE_DIALOG"]),
    ...mapActions(["FETCH_ARCHIVE_SUB"]),
    fetchArchiveSub() {
      this.FETCH_ARCHIVE_SUB().then(() => {
        console.log("아카이브 불러오기 성공", this.archiveSub);
        this.items = this.archiveSub;
      });
    },
    restoreSub() {
      this.dialog = true;
      this.$emit("restore");
    },
    deleteSub() {
      console.log("dkdkdkkd11111");
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
</style>