<template>
  <v-card outlined height="730">
    <v-toolbar flat class="blue-grey lighten-5">
      <v-icon left>mdi-archive-arrow-down-outline</v-icon>&nbsp;
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
      no-data-text="보관된 업무 없음"
      class="ma-5"
    >
      <template v-slot:default="props">
        <div class="mb-6">
          <v-card
            outlined
            v-for="item in props.items"
            :key="item.taskId"
            class="ma-4"
          >
            <v-card-title class="subheading pa-2">
              {{ item.projectTitle }}
              <v-spacer></v-spacer>
              <v-icon v-if="item.usePublic" small
                >mdi-lock-open-variant-outline</v-icon
              >
              <v-icon v-if="!item.usePublic" small>mdi-lock-outline</v-icon>
            </v-card-title>
            <v-divider></v-divider>
            <v-card-text class="archive-font mt-2 pa-1 mx-2">
              {{ item.title }}
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn small text @click.prevent="restoreSuper(item.taskId)"
                >복구</v-btn
              >
              <v-btn small text @click.prevent="openDelDialog(item.taskId)"
                >삭제</v-btn
              >
            </v-card-actions>
          </v-card>
        </div>
      </template>

      <template v-slot:footer>
        <template v-if="numberOfPages != 0">
          <v-row align="center" justify="center">
            <v-btn
              text
              x-small
              color="blue-grey"
              class="ma-2 white--text"
              @click="formerPage"
            >
              <v-icon>mdi-chevron-left</v-icon>
            </v-btn>
            <span class="ma-2 grey--text"
              >{{ page }} / {{ numberOfPages }} 페이지</span
            >
            <v-btn
              text
              x-small
              color="blue-grey"
              class="ma-2 white--text"
              @click="nextPage"
            >
              <v-icon>mdi-chevron-right</v-icon>
            </v-btn>
          </v-row>
        </template>
      </template>
    </v-data-iterator>
    <v-dialog v-model="openArcDialog" persistent max-width="290">
      <v-card>
        <v-card-title class="subheading font-weight-bold"
          >업무를 삭제하시겠습니까?</v-card-title
        >
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn text small @click="openArcDialog = false">취소</v-btn>
          <v-btn text small @click="deleteSuperTask()">확인</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </v-card>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import { eventBus } from "../../main.js";

export default {
  data() {
    return {
      itemsPerPageArray: [4, 8, 12],
      sortDesc: false,
      page: 1,
      itemsPerPage: 4,
      sortBy: "name",
      items: [],
      openArcDialog: false,
      delId: "",
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
  },
  methods: {
    ...mapMutations(["SET_IS_ARCHIVE_DIALOG"]),
    ...mapActions([
      "FETCH_ARCHIVE_SUPER",
      "DELETE_ARCHIVE_SUPER",
      "RESTORE_ARCHIVE_SUPER",
    ]),
    fetchArchiveSuper() {
      this.FETCH_ARCHIVE_SUPER().then(() => {
        console.log("슈퍼 아카이브", this.archiveSuper);
        this.items = this.archiveSuper;
      });
    },
    restoreSuper(id) {
      this.RESTORE_ARCHIVE_SUPER(id).then(() => {
        console.log("복구성공~~");
        this.fetchArchiveSuper();
      });
    },
    openDelDialog(id) {
      this.openArcDialog = true;
      this.delId = id;
    },
    deleteSuperTask() {
      this.DELETE_ARCHIVE_SUPER(this.delId).then(() => {
        this.fetchArchiveSuper();
        eventBus.$emit("reloading");
        this.openArcDialog = false;
      });
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
