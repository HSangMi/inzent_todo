<template>
  <v-dialog v-model="openArcDialog" max-width="290">
    <v-card>
      <v-card-text>업무를 삭제하시겠습니까?</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn text flat="flat" @click="deleteTask()">확인</v-btn>
        <v-btn text flat="flat" @click="SET_IS_ARCHIVE_DIALOG(false)">취소</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data() {
    return {};
  },
  computed: {
    ...mapState(["openArcDialog"]),
  },
  methods: {
    ...mapMutations(["SET_IS_ARCHIVE_DIALOG"]),
    deleteTask() {
      console.log("확인");
      this.SET_IS_ARCHIVE_DIALOG(false);
    },
  },
};
</script>

<style>
</style>