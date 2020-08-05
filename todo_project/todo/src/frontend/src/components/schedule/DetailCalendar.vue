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
        <span class="headline px-7 py-4">{{date}}</span>
      </v-card-title>
      <v-card-text class="py-0">
        <template v-if="clickDateList.length==0">업무 일정이 없습니다.</template>
        <template v-else>
          <v-timeline dense>
            <template v-for="item in clickDateList">
              <v-timeline-item
                :key="item.pid"
                color="teal lighten-3"
                small
                class="mb-5"
                @click="getSub(item.pid)"
              >
                <span class="detail-font">{{item.prjTitle}}</span>
                <v-spacer></v-spacer>
                <v-chip
                  v-if="item.pstate == 'P' "
                  class="text-center"
                  x-small
                  label
                  color="blue"
                  text-color="white"
                >진행</v-chip>
                <v-chip
                  v-if="item.pstate == 'W'"
                  class="text-center"
                  x-small
                  label
                  color="yellow"
                >대기</v-chip>
                <v-chip v-if="item.pstate == 'H' " class="text-center" x-small label>보류</v-chip>
                <v-chip
                  v-if="item.pstate == 'E'"
                  class="text-center"
                  x-small
                  label
                  color="red"
                  text-color="white"
                >긴급</v-chip>
                <v-chip
                  v-if="item.pstate == 'C'"
                  class="text-center"
                  x-small
                  label
                  color="green"
                  text-color="white"
                >완료</v-chip>
                <span @click="getSub(item.pid)" class="title-font">{{item.ptitle}}</span>
                <v-spacer></v-spacer>
                <!-- <v-spacer></v-spacer>
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
                </v-list-item-subtitle>-->
                <!-- <v-icon small class="my-5" v-show="item.pusePublic">mdi-lock-open-variant-outline</v-icon>
                <v-icon small class="my-5" v-show="!item.pusePublic">mdi-lock-outline</v-icon>-->
              </v-timeline-item>
            </template>
          </v-timeline>
        </template>
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
  props: ["date"],
  data: () => ({
    dateList: "",
  }),
  created() {
    console.log("날짜는?", this.date);
  },
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
.title-font {
  font-size: 14px;
}
.user-list {
  padding-left: 15px;
}
.user-avatar {
  margin-left: -5px;
}
</style>
