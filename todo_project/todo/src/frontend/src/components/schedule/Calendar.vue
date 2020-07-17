<template>
  <v-row>
    <v-col cols="2">
      <v-card class="border px-0" outlined>
        <div class="ma-1">
          <v-treeview
            v-model="selection"
            :items="items"
            :selection-type="selectionType"
            selectable
            return-object
            @input="chkFilter()"
          ></v-treeview>
        </div>
        <div class="text-xs-center px-3">
          <v-btn class="mb-3 mx-0" color="blue-grey" block small dark>RESET</v-btn>
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
                @click:more="showEvent"
                @click:day="showEvent"
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
        name: "PROJECT",
        children: []
      },
      {
        id: 2,
        name: "MANAGER",
        children: []
      },
      {
        id: 3,
        name: "IS_PUBLIC",
        children: [
          { id: "true", name: "Public", value: true },
          { id: "false", name: "Private" }
        ]
      }
      // {
      //   id: 4,
      //   name: "외부 일정",
      //   children: [],
      // },
    ],
    checked: ["PJ2020070102290349"]
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
      "IS_CLICK_DATE"
    ]),
    ...mapActions([
      "FETCH_CALENDAR_LIST",
      "FETCH_CALENDAR_CLICKDATE",
      "FETCH_FILTER",
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
    chkFilter() {
      console.log(this.selection);
      let calData = [];
      this.selection.forEach(item => {
        calData.push(item.id);
      });
      this.ADD_CAL_FILTER_ITEM(calData).then(() => {
        console.log("성공?!");
      });
    },
    // test() {
    //   console.log("dldldldl");
    //   return [1];
    // },
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
</style>
