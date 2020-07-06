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
                <!-- 일정추가버튼 -->
                <v-btn
                  class="mx-3"
                  outlined
                  fab
                  dark
                  small
                  color="blue"
                  @click.prevent="dialog = true"
                >
                  <v-icon dark>mdi-plus</v-icon>
                </v-btn>
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
                @click:more="showEvent"
                @click:date="showEvent"
                @change="updateRange"
              ></v-calendar>
              <!-- 달력 끝 -->
              <!-- 상세모달 창 시작 -->
              <v-dialog
                persistent
                max-width="600px"
                v-model="selectedOpen"
                :close-on-content-click="false"
                :activator="selectedElement"
                offset-x
              >
                <v-card>
                  <v-card-title>
                    <span class="headline">DETAILS TASKS</span>
                  </v-card-title>
                  <v-card-text>
                    <v-container>
                      <v-divider></v-divider>
                      <v-list-item
                        three-line
                        v-for="item in this.calendarList"
                        :key="item.ctitle"
                        @click="true"
                      >
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
                    </v-container>
                  </v-card-text>
                  <v-card-actions>
                    <v-spacer></v-spacer>
                    <v-btn color="blue darken-1" text @click="selectedOpen = false">Close</v-btn>
                  </v-card-actions>
                </v-card>
              </v-dialog>
              <!-- 상세모달 창 끝 -->
              <!-- 일정추가 모달 시작 -->
              <!-- <add-calendar :openDialog="isOpenCalendar" /> -->
              <v-dialog v-model="dialog" max-width="700px">
                <v-stepper v-model="e1">
                  <v-divider></v-divider>
                  <v-stepper-header>
                    <v-stepper-step :complete="e1 > 1" step="1">SELECT PROJECT</v-stepper-step>

                    <v-divider></v-divider>

                    <v-stepper-step :complete="e1 > 2" step="2">ADD TASK</v-stepper-step>
                  </v-stepper-header>

                  <v-stepper-items>
                    <v-stepper-content step="1">
                      <v-card class="mb-12" height="200px">
                        <v-card-title class="text-h5">1. SELCT PROJECT</v-card-title>
                        <v-select :items="selectProjects" label="PROJECT" outlined></v-select>
                        <v-select :items="selectTasks" label="TASKS" outlined></v-select>
                      </v-card>
                      <!-- <v-icon absolute right large @click="e1 = 2">mdi-chevron-right</v-icon> -->
                      <v-btn text @click="dialog=false">CANCEL</v-btn>
                      <v-btn absolute right class="ma-2" color="primary" fab x-small dark  @click="e1 = 2"><v-icon>mdi-chevron-right</v-icon></v-btn>
                    </v-stepper-content>

                    <v-stepper-content step="2">
                      <v-card class="mb-12" height="200px">
                        <v-card-title class="text-h5">2. ADD TASK</v-card-title>
                      </v-card>

                      <!-- <v-btn class="ma-2" tile outlined color="primary" @click="e1 = 1">PREV</v-btn> -->
                      <v-btn color="primary">ADD</v-btn>
                      <v-btn text @click="dialog=false">CANCEL</v-btn>
                      <v-btn absolute right class="ma-2" color="primary" fab x-small dark  @click="e1 = 1"><v-icon>mdi-chevron-left</v-icon></v-btn>
                    </v-stepper-content>
                  </v-stepper-items>
                </v-stepper>
              </v-dialog>
              <!-- 일정추가 모달 끝 -->
            </v-sheet>
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapActions } from "vuex";
// import AddCalendar from "./AddCalendar.vue";

export default {
  // components: {
  //   AddCalendar
  // },
  data: () => ({
    selected: [2],
    //////////// 업무 등록 ////////////
    e1: 1,
    // isOpenCalendar: false,
    selectProjects: [], // 업무추가에서 선택할 프로젝트 아이템
    selectTasks: [], // 업무추가에서 선택할 업무대 아이템
    dialog: false,
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

      for (var i = 0; i < this.calendarList.length; i++) {
        ////////////////////////
        this.selectProjects.push(this.calendarList[i].prjTitle);
        this.selectTasks.push(this.calendarList[i].ptitle);

        const taskName =
          "[" +
          this.calendarList[i].ptitle +
          "] " +
          this.calendarList[i].ctitle;
        const startDate = new Date(this.calendarList[i].startDate);
        const endDate = new Date(this.calendarList[i].endDate);
        // console.log('aaaa',this.$refs.calendar.getDate())
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
  computed: {
    // 사용할 mapstate 불러옴
    ...mapState({
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
    showEvent({ date }) {
      // 모달창 이벤트
      const open = () => {
        this.focus = date; // 들어온 해당 날짜
        console.log(this.focus);

        setTimeout(() => (this.selectedOpen = true), 10);
      };

      if (this.selectedOpen) {
        this.selectedOpen = false;
        setTimeout(open, 10);
      } else {
        open();
      }

      // nativeEvent.stopPropagation();
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
      // 상태에 따른 색상
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