<template>
  <v-dialog
    persistent
    max-width="600px"
    v-model="isDetailCalendar"
    :close-on-content-click="false"
    offset-x
  >
    <v-card>
      <v-card-title>
        <span class="headline">DETAILS TASKS</span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <v-divider></v-divider>
          <template v-if="clickDateList">
            <v-list-item three-line v-for="item in clickDateList" :key="item.ctitle" @click="true">
              <v-list-item-avatar>
                <v-icon color="pink">event</v-icon>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title v-text="item.ptitle"></v-list-item-title>
                <v-list-item-subtitle v-text="item.ctitle"></v-list-item-subtitle>
                <v-list-item-subtitle v-text="item.managerName"></v-list-item-subtitle>
              </v-list-item-content>
              <v-icon v-show="item.usePublic">mdi-sort-variant</v-icon>
              <v-icon v-show="!item.usePublic">mdi-sort-variant-lock</v-icon>
            </v-list-item>
            <v-divider></v-divider>
          </template>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="SET_IS_DETAIL_CALENDAR(false)">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  computed: {
    ...mapState({
      clickDateList: "getClickDateList"
    }),
    ...mapState(["isDetailCalendar"])
  },
  methods: {
    ...mapMutations(["SET_IS_DETAIL_CALENDAR"])
  }
};
</script>

<style>
</style>