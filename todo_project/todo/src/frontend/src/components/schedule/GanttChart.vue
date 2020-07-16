<template>
  <v-row>
    <v-col cols="2">
      <v-card class="border px-0" outlined>
        <div class="ma-1">
          <v-treeview
            v-model="selection"
            :items="filter"
            :selection-type="selectionType"
            selectable
            return-object
          ></v-treeview>
        </div>
        <div class="text-xs-center px-3">
          <v-btn class="mb-3 mx-0" color="blue-grey" block small dark>RESET</v-btn>
        </div>
      </v-card>
    </v-col>
    <v-col cols="10">
      <div style="width:98%; height:700px;">
        <gantt-elastic :tasks="tasks" :options="options"></gantt-elastic>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapActions } from "vuex";
import GanttElastic from "gantt-elastic";

export default {
  components: {
    ganttElastic: GanttElastic
    // ganttElasticFooter: { template: `<span>your footer</span>` },
  },
  data() {
    function getDate(hours) {
      const currentDate = new Date();
      const currentYear = currentDate.getFullYear();
      const currentMonth = currentDate.getMonth();
      const currentDay = currentDate.getDate();
      const timeStamp = new Date(
        currentYear,
        currentMonth,
        currentDay,
        0,
        0,
        0
      ).getTime();
      return new Date(timeStamp + hours * 60 * 60 * 1000).getTime();
    }
    let tasks = [
      {
        id: 1,
        label: "Make some noise",
        user: "user1",
        start: getDate(-24 * 5),
        duration: 15 * 24 * 60 * 60 * 1000,
        progress: 85,
        type: "project"
        //collapsed: true,
      },
      {
        id: 2,
        label: "With great power comes great responsibility",
        user: "user2",
        parentId: 1,
        start: getDate(-24 * 4),
        duration: 4 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "milestone",
        collapsed: true,
        style: {
          base: {
            fill: "#1EBC61",
            stroke: "#0EAC51"
          }
          /*'tree-row-bar': {
              fill: '#1EBC61',
              stroke: '#0EAC51'
            },
            'tree-row-bar-polygon': {
              stroke: '#0EAC51'
            }*/
        }
      },
      {
        id: 4,
        label: "Put that toy AWAY!",
        user: "user3",
        start: getDate(-24 * 2),
        duration: 2 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "task",
        dependentOn: [3]
      },
      {
        id: 4,
        label: "Put that toy AWAY!",
        user: "user4",
        start: getDate(-24 * 2),
        duration: 2 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "task",
        dependentOn: [3]
      },
      {
        id: 4,
        label: "Put that toy AWAY!",
        user: "user5",
        start: getDate(-24 * 2),
        duration: 2 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "task",
        dependentOn: [3]
      }
    ];

    let options = {
      maxRows: 100,
      maxHeight: 650,
      title: {
        label: "Your project title as html (link or whatever...)",
        html: false
      },
      row: {
        height: 24
      },
      calendar: {
        hour: {
          display: false
        }
      },
      chart: {
        progress: {
          bar: false
        },
        expander: {
          display: true
        }
      },
      taskList: {
        expander: {
          straight: false
        },
        columns: [
          {
            id: 2,
            label: "TASKS",
            value: "label",
            width: 200,
            expander: true,
            html: true,
            events: {
              click({ data }) {
                alert("description clicked!\n" + data.label);
              }
            }
          },
          {
            id: 3,
            label: "MEMBER",
            value: "user",
            width: 130,
            html: true
          },
          {
            id: 3,
            label: "DUEDATE",
            value: "2020-06-23",
            width: 78
          },
          {
            id: 5,
            label: "%",
            value: "progress",
            width: 35,
            style: {
              "task-list-header-label": {
                "text-align": "center",
                width: "100%"
              },
              "task-list-item-value-container": {
                "text-align": "center",
                width: "100%"
              }
            }
          }
        ]
      }
    };
    //////////////// Filter ///////////////////
    let filter = [
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
          { id: "true", name: "PUBLIC" },
          { id: "false", name: "PRIVATE" }
        ]
      }
    ];
    //////////////// Filter END ///////////////////
    return {
      tasks: tasks,
      options: options,
      filter: filter
    };
  },
  created() {
    // store -> actions
    this.fetchFilter();
  },
  computed: {
    // 사용할 mapstate 불러옴
    ...mapState({
      getFilter: "getFilter"
    })
  },
  methods: {
    ...mapActions(["FETCH_FILTER"]),

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
          this.filter[0].children.push({
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
          this.filter[1].children.push({
            id: memArray[j].userId,
            name: memArray[j].userName
          });
        }
      });
    }
  }
};
</script>

<style></style>
