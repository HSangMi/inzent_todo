<template>
  <v-row>
    <v-col cols="2">
      <v-card class="border px-2">
        <div class="ma-3">
          <!-- <v-treeview selectable :items="items"></v-treeview> -->
          <v-treeview
            v-model="selection"
            :items="items"
            :selection-type="selectionType"
            selectable
            return-object
            open-all
          ></v-treeview>
        </div>
        <div class="text-xs-center">
          <v-btn class="mb-2 mx-1" color="blue-grey" block small dark>RESET</v-btn>
        </div>
        <!-- <v-col class="pa-6" cols="6">
              <template v-if="!selection.length">No nodes selected.</template>
              <template v-else>
                <div v-for="node in selection" :key="node.id">{{ node.name }}</div>
              </template>
        </v-col>-->
      </v-card>
    </v-col>
    <v-col cols="10">
      <v-card class="border">
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
    DetailCalendar
  },
  data: () => ({
    selected: [2],
    focus: "",
    type: "month",
    selectedEvent: {},
    selectedElement: null,
    selectedOpen: false,
    events: [],
    colors: ["#BFC9CA", "#5DADE2", "#82E0AA", "#F7DC6F", "#F1948A"],
    //////////////// Filter ///////////////////
    selectionType: "leaf",
    selection: [],
    items: [
      {
        id: 1,
        name: "프로젝트",
        children: []
      },
      {
        id: 2,
        name: "담당자",
        children: []
      },
      {
        id: 3,
        name: "공개 여부",
        children: [
          { id: "true", name: "공개" },
          { id: "false", name: "비공개" }
        ]
      }
      // {
      //   id: 4,
      //   name: "외부 일정",
      //   children: [],
      // },
    ]
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
      getFilter: "getFilter"
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
      "IS_CLICK_DATE"
    ]),
    ...mapActions([
      "FETCH_CALENDAR_LIST",
      "FETCH_CALENDAR_CLICKDATE",
      "FETCH_FILTER"
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
    showEvent({ date }) {
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
    updateRange() {
      this.fetchCalendarInfo();
    },
    fetchFilter() {
      this.FETCH_FILTER().then(() => {
        let list = this.getFilter;

        /// 프로젝트 id 중복 제거
        var prjArray = [];
        var prjObj = {};
        for (var i in list) {
          prjObj[list[i]["prjId"]] = list[i];
        }

        for (i in prjObj) {
          prjArray.push(prjObj[i]);
        }

        for (i in prjArray) {
          this.items[0].children.push({
            id: prjArray[i].prjId,
            name: prjArray[i].prjTitle
          });
        }

        /// 담당자 중복 제거
        var memArray = [];
        var memObj = {};
        for (var j in list) {
          memObj[list[j]["userId"]] = list[j];
        }
        for (j in memObj) {
          memArray.push(memObj[j]);
        }

        for (j in memArray) {
          this.items[1].children.push({
            id: memArray[j].userId,
            name: memArray[j].userName
          });
        }
      });
    },
    fetchCalendarInfo() {
      this.FETCH_CALENDAR_LIST().then(() => {
        const events = [];
        for (var i = 0; i < this.calendarList.length; i++) {
          const taskName =
            " [" +
            this.calendarList[i].ptitle +
            "] " +
            this.calendarList[i].ctitle;

          const cStartDate = new Date(this.calendarList[i].cstartDate);
          const cEndDate = new Date(this.calendarList[i].cendDate);
          // const pStartDate = new Date(this.calendarList[i].pstartDate);
          // const pEndDate = new Date(this.calendarList[i].pendDate);
          events.push({
            name: taskName, // 타이틀
            start: cStartDate, // 시작일
            end: cEndDate, // 마감일
            color: this.colors[this.colorState(this.calendarList[i].cstate)] // 색상
          });
          // events.push({
          //   name: this.calendarList[i].ptitle + "<v-icon>mdi-plus</v-icon>", // 타이틀
          //   start: pStartDate, // 시작일
          //   end: pEndDate, // 마감일
          //   color: this.colors[this.colorState(this.calendarList[i].pstate)], // 색상
          // });
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
</style>
