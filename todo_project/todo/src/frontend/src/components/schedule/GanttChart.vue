<template>
  <v-row>
    <v-col cols="2">
      <v-card outlined max-height="838" class="overflow-y-auto">
        <div class="mx-2">
          <v-subheader>PROJECT</v-subheader>
          <v-list class="project-filter">
            <v-list-item v-for="item in projectFilter" :key="item.prjId">
              <v-list-item-content class="px-0">
                <v-checkbox class="px-5" v-model="selection" :value="item.prjId">
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
                <v-checkbox class="px-5" v-model="selection" :value="item.userId">
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
                <v-checkbox class="px-5" v-model="selection" :value="this.usePublic[0]">
                  <template v-slot:label>
                    <span class="font-filter">public</span>
                  </template>
                </v-checkbox>
                <v-checkbox class="px-5" v-model="selection" :value="this.usePublic[1]">
                  <template v-slot:label>
                    <span class="font-filter">private</span>
                  </template>
                </v-checkbox>
              </v-list-item-content>
            </v-list-item>
          </v-list>
          <v-divider></v-divider>
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
    let projectFilter = [];
    let managerFilter = [];
    let usePublic = [true, false];
    let selection = [];
    //////////////// Filter END ///////////////////
    return {
      tasks: tasks,
      options: options,
      projectFilter: projectFilter,
      managerFilter: managerFilter,
      usePublic: usePublic,
      selection: selection
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
