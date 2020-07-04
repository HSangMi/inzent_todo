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
                <v-toolbar-title v-if="$refs.calendar">{{ $refs.calendar.title }}</v-toolbar-title>
                <v-spacer></v-spacer>
              </v-toolbar>
            </v-sheet>
            <!-- 달력 시작 -->
            <v-sheet height="600">
              <v-calendar
                ref="calendar"
                v-model="focus"
                color="primary"
                :events="events"
                :event-color="getEventColor"
                :type="type"
                @click:event="showEvent"
                @click:more="showEvent"
                @click:date="showEvent"
                @change="updateRange"
              ></v-calendar>
              <!-- 달력 끝 -->
              <!-- 모달창 시작 -->
              <v-menu
                v-model="selectedOpen"
                :close-on-content-click="false"
                :activator="selectedElement"
                offset-x
              >
                <v-card color="grey lighten-4" min-width="350px" flat>
                  <v-toolbar :color="selectedEvent.color" dark>
                    <v-btn icon>
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                    <v-toolbar-title v-html="selectedEvent.name"></v-toolbar-title>
                    <v-spacer></v-spacer>
                    <v-btn icon>
                      <v-icon>mdi-heart</v-icon>
                    </v-btn>
                    <v-btn icon>
                      <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                  </v-toolbar>
                  <v-card-text>
                    <span v-html="selectedEvent.details"></span>
                  </v-card-text>
                  <v-card-actions>
                    <v-btn text color="secondary" @click="selectedOpen = false">Cancel</v-btn>
                  </v-card-actions>
                </v-card>
              </v-menu>
              <!-- 모달창 끝 -->
            </v-sheet>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  data: () => ({
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
          { id: 4, name: "Webstorm : app" }
        ]
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
                  { id: 9, name: "bootstrap : ts" }
                ]
              }
            ]
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
                  { id: 14, name: "v-window : ts" }
                ]
              }
            ]
          }
        ]
      },
      {
        id: 15,
        name: "공개여부",
        children: [
          { id: 16, name: "공개" },
          { id: 17, name: "개인용" }
        ]
      }
    ]
    //////////////// Filter END ///////////////////
  }),
  created() {
    // store -> actions
    this.FETCH_CALENDAR_LIST().then(() => {
      const events = [];

      console.log(this.colors);

      for (var i = 0; i < this.calendarList.length; i++) {
        const startDate = new Date(this.calendarList[i].startDate);
        const endDate = new Date(this.calendarList[i].endDate);

        events.push({
          name: this.calendarList[i].ctitle, // 타이틀
          start: startDate, // 시작일
          end: endDate, // 마감일
          color: this.colors[this.colorState(this.calendarList[i].state)] // 색상
        });
      }

      this.events = events;
    });
  },
  computed: {
    ...mapState({
      // 사용할 mapstate 불러옴
      calendarList: "calendarList"
    })
  },
  mounted() {
    this.$refs.calendar.checkChange();
  },
  methods: {
    ...mapActions(["FETCH_CALENDAR_LIST"]), // 사용할 mapactions 등록

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
    showEvent({ nativeEvent, event }) {
      const open = () => {
        this.selectedEvent = event;
        this.selectedElement = nativeEvent.target;
        setTimeout(() => (this.selectedOpen = true), 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      nativeEvent.stopPropagation();
    },
    updateRange() {
      const events = [];

      for (let i = 0; i < this.calendarList.length; i++) {
        const startDate = new Date(this.calendarList[i].startDate);
        const endDate = new Date(this.calendarList[i].endDate);

        events.push({
          name: this.calendarList[i].ctitle,
          start: startDate,
          end: endDate,
          color: this.colors[this.colorState(this.calendarList[i].state)]
        });
      }

      this.events = events;
    },
    colorState(state) {
      switch (state) {
        case "h":
          return 0;
        case "p":
          return 1;
        case "c":
          return 2;
        case "w":
          return 3;
        case "e":
          return 4;
      }
    }
  }
};
</script>

<style>
</style>