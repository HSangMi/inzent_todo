<template>
  <v-dialog
    persistent
    max-width="600px"
    v-model="isDetailCalendar"
    :close-on-content-click="false"
    offset-x
  >
    <v-card>
      <v-card-title class="pa-2">
        <span class="headline px-7 py-4">ㅎㅎ</span>
      </v-card-title>
      <v-card-text>
        <v-container class="py-0">
          <v-divider></v-divider>
          <template v-if="clickDateList">
            <v-list three-line>
              <v-list-item-group>
                <template v-for="(item, index) in clickDateList">
                  <v-list-item :key="item.pid" @click="getSub(item.pid)">
                    <v-list-item-content>
                      <v-list-item-subtitle class="pb-2">
                        <span class="detail-font">{{item.prjTitle}}</span>
                      </v-list-item-subtitle>
                      <v-spacer></v-spacer>
                      <v-list-item-title class="text-h5 pb-3" v-text="item.ptitle"></v-list-item-title>
                      <v-spacer></v-spacer>
                      <v-list-item-subtitle v-if="item.pstartDate != '' && item.pendDate != ''">
                        <span class="detail-font">{{item.pstartDate}} ~ {{item.pendDate}}</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.pstartDate == '' && item.pendDate !=''">
                        <span class="detail-font">{{item.pstartDate}} ~ 미정</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.pstartDate != '' && item.pendDate == ''">
                        <span class="detail-font">미정 ~ {{item.pendDate}}</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.pstartDate == '' && item.pendDate == ''">
                        <span class="detail-font">기간 미정</span>
                      </v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-chip
                        v-if="item.pstate == 'P' "
                        class="text-center ma-5 my-5"
                        small
                        color="blue"
                        text-color="white"
                      >진행</v-chip>
                      <v-chip
                        v-if="item.pstate == 'W'"
                        class="text-center ma-5 my-5"
                        small
                        color="yellow"
                      >대기</v-chip>
                      <v-chip v-if="item.pstate == 'H' " class="text-center ma-5" small>보류</v-chip>
                      <v-chip
                        v-if="item.pstate == 'E'"
                        class="text-center ma-2 my-5"
                        small
                        color="red"
                        text-color="white"
                      >긴급</v-chip>
                      <v-chip
                        v-if="item.pstate == 'C'"
                        class="text-center ma-5 my-5"
                        small
                        color="green"
                        text-color="white"
                      >완료</v-chip>
                    </v-list-item-action>
                    <v-list-item-action>
                      <v-icon
                        class="my-5"
                        small
                        v-show="item.pusePublic"
                      >mdi-lock-open-variant-outline</v-icon>
                      <v-icon class="my-5" small v-show="!item.pusePublic">mdi-lock-outline</v-icon>
                    </v-list-item-action>
                  </v-list-item>
                  <v-divider v-if="index + 1 < clickDateList.length" :key="index"></v-divider>
                </template>
              </v-list-item-group>
            </v-list>
            <!-- <v-list-item-avatar></v-list-item-avatar> -->
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
import { mapMutations, mapState, mapActions } from "vuex";

export default {
  data: () => ({
    dateList: "",
  }),
  created() {},
  computed: {
    ...mapState({
      clickDateList: "getClickDateList",
    }),
    ...mapState(["isDetailCalendar"]),
  },
  methods: {
    ...mapMutations(["SET_IS_DETAIL_CALENDAR", "SET_IS_DETAIL_SUB"]),
    ...mapActions(["FETCH_CALENDAR_EVENT"]),
    getSub(id) {
      const superId = id;
      console.log("aaaaaa", superId);
      this.FETCH_CALENDAR_EVENT(superId);
      this.SET_IS_DETAIL_SUB(true);
    },
  },
};
</script>

<style scoped>
.detail-font {
  font-size: 11px;
  color: cadetblue;
}
.user-font {
  font-size: 11px;
}
.user-list {
  padding-left: 15px;
}
.user-avatar {
  margin-left: -5px;
}
</style>
