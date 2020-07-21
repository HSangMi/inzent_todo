<template>
  <v-row>
    <v-col cols="2">
      <v-card outlined max-height="838" class="overflow-y-auto">
        <div class="mx-2">
          <v-subheader>PROJECT</v-subheader>
          <v-list class="project-filter">
            <v-list-item v-for="item in projectFilter" :key="item.prjId">
              <v-list-item-content class="px-0">
                <v-checkbox
                  class="px-5"
                  v-model="prjSelection"
                  :value="item.prjId"
                  @change="chkFilter()"
                >
                  <template v-slot:label>
                    <span class="font-filter">{{item.prjTitle}}</span>
                  </template>
                </v-checkbox>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-subheader>MANAGER</v-subheader>
          <v-list class="project-filter">
            <v-list-item v-for="item in managerFilter" :key="item.userId">
              <v-list-item-content class="px-0">
                <v-checkbox
                  class="px-5"
                  v-model="memSelection"
                  :value="item.userId"
                  @change="chkFilter()"
                >
                  <template v-slot:label>
                    <span class="font-filter">{{item.userName}}</span>
                  </template>
                </v-checkbox>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
          <v-subheader>USE_PUBLIC</v-subheader>
          <v-list class="project-filter">
            <v-list-item>
              <v-list-item-content class="px-0">
                <v-radio-group v-model="publicSelection" @change="chkFilter()">
                  <v-radio class="px-5" :value="true">
                    <template v-slot:label>
                      <span class="font-filter">공개</span>
                    </template>
                  </v-radio>
                  <v-radio class="px-5" :value="false">
                    <template v-slot:label>
                      <span class="font-filter">비공개</span>
                    </template>
                  </v-radio>
                </v-radio-group>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
        </div>
        <div class="text-xs-center px-3">
          <v-btn class="ma-3 mx-0" color="blue-grey" block small dark>RESET</v-btn>
        </div>
      </v-card>
    </v-col>
    <v-col cols="10">
      <v-card class="border" outlined>
        <v-row class="fill-height">
          <v-col>
            <v-sheet height="64">
              <v-toolbar flat color="white">
                <!-- 오늘날짜이동 -->
                <v-btn outlined class="mr-4" color="grey darken-2" @click="setToday">Today</v-btn>
                <!-- < 이전 버튼 -->
                <v-btn fab text small color="grey darken-2" @click="prev">
                  <v-icon small>mdi-chevron-left</v-icon>
                </v-btn>
                <!-- > 다음 버튼 -->
                <v-btn fab text small color="grey darken-2" @click="next">
                  <v-icon small>mdi-chevron-right</v-icon>
                </v-btn>
                <!-- 몇월인지 타이틀 -->
                <v-toolbar-title v-if="$refs.calendar">
                  {{
                  $refs.calendar.title
                  }}
                </v-toolbar-title>
                <v-spacer></v-spacer>
                <!-- 업무추가버튼 -->
                <v-btn
                  class="mx-3"
                  outlined
                  fab
                  dark
                  small
                  color="blue"
                  @click.prevent="SET_IS_ADD_CALENDAR(true)"
                >
                  <v-icon dark>mdi-plus</v-icon>
                </v-btn>
              </v-toolbar>
            </v-sheet>
            <!-- 달력 시작 -->
            <v-sheet height="750">
              <v-calendar
                ref="calendar"
                v-model="focus"
                color="primary"
                :events="events"
                :event-color="getEventColor"
                :type="type"
                @click:event="showEvent"
                @click:date="showDate"
                @change="updateRange"
              ></v-calendar>
              <!-- 달력 끝 -->
              <!-- 상세모달 창 시작 -->
              <detail-calendar />
              <!-- 상세모달 창 끝 -->
              <!-- 업무소 모달 시작 -->
              <detail-cal-event />
              <!-- 업무소 모달 끝 -->
              <!-- 일정추가 모달 시작 -->
              <add-calendar />
              <!-- 일정추가 모달 끝 -->
            </v-sheet>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapMutations, mapActions } from "vuex";
import AddCalendar from "./AddCalendar.vue";
import DetailCalendar from "./DetailCalendar.vue";
import DetailCalEvent from "./DetailCalEvent.vue";

export default {
  components: {
    AddCalendar,
    DetailCalendar,
    DetailCalEvent
  },
  data: () => ({
    focus: "",
    type: "month",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    colors: ["#BFC9CA", "#5DADE2", "#82E0AA", "#F7DC6F", "#F1948A"],
    //////////////// Filter ///////////////////
    projectFilter: [],
    managerFilter: [],
    prjSelection: [],
    memSelection: [],
    publicSelection: true
    //////////////// Filter END ///////////////////
  }),
  created() {
    // store -> actions
    this.fetchCalendarInfo();
    this.fetchFilter();
  },
  computed: {
    // 사용할 mapstate 불러옴
    ...mapState({
      calendarList: "calendarList",
      getClickDateList: "getClickDateList",
      getFilter: "getFilter",
      getChkFilterItem: "getChkFilterItem",
      calFilterItem: "calFilterItem"
    })
    // ...mapState(["clickDate"])
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    ...mapMutations([
      "SET_IS_ADD_CALENDAR",
      "SET_IS_DETAIL_CALENDAR",
      "SET_IS_DETAIL_SUB",
      "IS_CLICK_DATE"
    ]),
    ...mapActions([
      "FETCH_CALENDAR_LIST",
      "FETCH_CALENDAR_CLICKDATE",
      "FETCH_CALENDAR_EVENT",
      "FETCH_FILTER",
      "FETCH_CHK_FILTER_ITEM",
      "ADD_CAL_FILTER_ITEM"
    ]),
    // 사용할 mapactions 등록

    getEventColor(event) {
      return event.color;
    },
    setToday() {
      // 오늘날짜로 이동
      this.focus = "";
    },
    prev() {
      // 이전 달 이동
      this.$refs.calendar.prev();
    },
    next() {
      // 다음 달 이동
      this.$refs.calendar.next();
    },
    showDate({ date }) {
      // 모달창 이벤트
      this.focus = date; // 들어온 해당 날짜
      const clickDate = {
        clickDate: this.focus
      };
      this.FETCH_CALENDAR_CLICKDATE(clickDate).then(() => {
        this.SET_IS_DETAIL_CALENDAR(true);
      });
      // this.IS_CLICK_DATE(this.focus);
    },
    showEvent({ event }) {
      // 해당 이벤트
      const superId = event.id;
      this.FETCH_CALENDAR_EVENT(superId);
      this.SET_IS_DETAIL_SUB(true);
    },
    updateRange() {
      this.fetchCalendarInfo();
    },
    fetchFilter() {
      this.FETCH_FILTER().then(() => {
        let list = this.getFilter;

        /// 프로젝트 id 중복 제거
        var prjObj = {};
        for (var i in list) {
          prjObj[list[i]["prjId"]] = list[i];
        }

        for (i in prjObj) {
          this.projectFilter.push(prjObj[i]);
        }

        /// 담당자 중복 제거
        var memObj = {};
        for (var j in list) {
          memObj[list[j]["userId"]] = list[j];
        }
        for (j in memObj) {
          this.managerFilter.push(memObj[j]);
        }
      });
    },
    fetchChkItem() {
      this.FETCH_CHK_FILTER_ITEM();
      console.log("확이이이인", this.getChkFilterItem);
    },
    chkFilter() {
      // let prjData = ":P=";
      // let memData = ":M=";
      // let useData = ":U=";
      // let prjResult = this.prjSelection.join(); // 배열 합치기
      // let memResult = this.memSelection.join(); // 배열 합치기
      // let useResult = this.publicSelection; // 배열 합치기
      // prjData += prjResult;
      // memData += memResult;
      // useData += useResult;
      // let calData = prjData + memData + useData;
      const calData = {
        prjData: this.prjSelection,
        memData: this.memSelection,
        useData: this.publicSelection
      };

      this.ADD_CAL_FILTER_ITEM(calData);
      console.log("성공?!");
    },
    fetchCalendarInfo() {
      this.FETCH_CALENDAR_LIST().then(() => {
        const events = [];
        for (var i = 0; i < this.calendarList.length; i++) {
          const taskName = this.calendarList[i].title;

          let startDate = null;
          let endDate = null;
          if (
            this.calendarList[i].startDate == "" ||
            this.calendarList[i].endDate == ""
          ) {
            startDate = new Date(this.calendarList[i].regDate);
            endDate = new Date(this.calendarList[i].regDate);
          } else {
            startDate = new Date(this.calendarList[i].startDate);
            endDate = new Date(this.calendarList[i].endDate);
          }
          events.push({
            id: this.calendarList[i].id,
            name: taskName, // 타이틀
            start: startDate, // 시작일
            end: endDate, // 마감일
            color: this.colors[this.colorState(this.calendarList[i].state)] // 색상
          });
        } // end for
        console.log(this.selectProjects);

        this.events = events;
      });
    },
    colorState(state) {
      // 상태에 따른 색상
      switch (state) {
        case "H":
          return 0;
        case "P":
          return 1;
        case "C":
          return 2;
        case "W":
          return 3;
        case "E":
          return 4;
      }
    }
  }
};
</script>

<style scoped>
.border {
  border: 1px solid #ccd1d1;
  /* height: 850px; */
}
.project-filter .v-input--selection-controls {
  margin-top: 0px;
}
.project-filter .v-list-item__content {
  padding: 0px;
}
.font-filter {
  font-size: 13px;
}
</style>
