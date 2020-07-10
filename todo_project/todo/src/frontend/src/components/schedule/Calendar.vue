<template>
  <v-row>
    <v-col cols="12" md="2">
      <v-card>
        <v-treeview selectable :items="items"></v-treeview>
      </v-card>
    </v-col>
    <v-col cols="12" md="10">
      <v-card style="width:95%; height:100%; border:1px solid #CCD1D1">
        <v-row class="fill-height">
          <v-col>
            <v-sheet height="64">
              <v-toolbar flat color="white">
                <!-- 오늘날짜이동 -->
                <v-btn
                  outlined
                  class="mr-4"
                  color="grey darken-2"
                  @click="setToday"
                  >Today</v-btn
                >
                <!-- < 이전 버튼 -->
                <v-btn fab text small color="grey darken-2" @click="prev">
                  <v-icon small>mdi-chevron-left</v-icon>
                </v-btn>
                <!-- > 다음 버튼 -->
                <v-btn fab text small color="grey darken-2" @click="next">
                  <v-icon small>mdi-chevron-right</v-icon>
                </v-btn>
                <!-- 몇월인지 타이틀 -->
                <v-toolbar-title v-if="$refs.calendar">{{
                  $refs.calendar.title
                }}</v-toolbar-title>
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
            <v-sheet height="680">
              <v-calendar
                ref="calendar"
                v-model="focus"
                color="primary"
                :events="events"
                :event-color="getEventColor"
                :type="type"
                @click:more="showEvent"
                @click:date="showEvent"
                @change="updateRange"
              ></v-calendar>
              <!-- 달력 끝 -->
              <!-- 상세모달 창 시작 -->
              <detail-calendar />
              <!-- 상세모달 창 끝 -->
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

export default {
  components: {
    AddCalendar,
    DetailCalendar,
  },
  data: () => ({
    selected: [2],
    //////////// 업무 등록 ////////////
    // e1: 1,
    // isOpenCalendar: false,
    // selectProjects: [], // 업무추가에서 선택할 프로젝트 아이템
    // selectTasks: [], // 업무추가에서 선택할 업무대 아이템
    // dialog: false,
    //////////////////
    focus: "",
    type: "month",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    colors: ["#BFC9CA", "#5DADE2", "#82E0AA", "#F7DC6F", "#F1948A"],
    //////////////// Filter ///////////////////
    items: [
      {
        id: 1,
        name: "프로젝트",
        children: [
          { id: 2, name: "Calendar : app" },
          { id: 3, name: "Chrome : app" },
          { id: 4, name: "Webstorm : app" },
        ],
      },
      {
        id: 5,
        name: "담당자",
        children: [
          {
            id: 6,
            name: "vuetify :",
            children: [
              {
                id: 7,
                name: "src :",
                children: [
                  { id: 8, name: "index : ts" },
                  { id: 9, name: "bootstrap : ts" },
                ],
              },
            ],
          },
          {
            id: 10,
            name: "material2 :",
            children: [
              {
                id: 11,
                name: "src :",
                children: [
                  { id: 12, name: "v-btn : ts" },
                  { id: 13, name: "v-card : ts" },
                  { id: 14, name: "v-window : ts" },
                ],
              },
            ],
          },
        ],
      },
      {
        id: 15,
        name: "공개여부",
        children: [
          { id: 16, name: "공개" },
          { id: 17, name: "개인용" },
        ],
      },
    ],
    //////////////// Filter END ///////////////////
  }),
  created() {
    // store -> actions
    this.fetchCalendarInfo();
  },
  computed: {
    // 사용할 mapstate 불러옴
    ...mapState({
      calendarList: "calendarList",
      getClickDateList: "getClickDateList",
    }),
    // ...mapState(["clickDate"])
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    ...mapMutations(["SET_IS_ADD_CALENDAR"]),
    ...mapMutations(["SET_IS_DETAIL_CALENDAR"]),
    ...mapMutations(["IS_CLICK_DATE"]),
    ...mapActions(["FETCH_CALENDAR_LIST", "FETCH_CALENDAR_CLICKDATE"]),
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
    showEvent({ date }) {
      // 모달창 이벤트
      this.focus = date; // 들어온 해당 날짜
      console.log("날짜날짜", this.focus);
      const clickDate = {
        clickDate: this.focus,
      };
      this.FETCH_CALENDAR_CLICKDATE(clickDate).then(() => {
        this.SET_IS_DETAIL_CALENDAR(true);
        console.log("result완료~~~~~~~~~~");
      });
      // this.IS_CLICK_DATE(this.focus);
    },
    updateRange() {
      this.fetchCalendarInfo();
    },
    fetchCalendarInfo() {
      this.FETCH_CALENDAR_LIST().then(() => {
        const events = [];
        console.log("???????", this.calendarList);
        for (var i = 0; i < this.calendarList.length; i++) {
          const taskName =
            this.calendarList[i].ctitle +
            " (" +
            this.calendarList[i].ptitle +
            ")";
          const cStartDate = new Date(this.calendarList[i].cstartDate);
          const cEndDate = new Date(this.calendarList[i].cendDate);
          const pStartDate = new Date(this.calendarList[i].pstartDate);
          const pEndDate = new Date(this.calendarList[i].pendDate);
          events.push({
            name: taskName, // 타이틀
            start: cStartDate, // 시작일
            end: cEndDate, // 마감일
            color: this.colors[this.colorState(this.calendarList[i].cstate)], // 색상
          });
          events.push({
            name: this.calendarList[i].ptitle + "<v-icon>mdi-plus</v-icon>", // 타이틀
            start: pStartDate, // 시작일
            end: pEndDate, // 마감일
            color: this.colors[this.colorState(this.calendarList[i].pstate)], // 색상
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
    },
  },
};
</script>

<style></style>
