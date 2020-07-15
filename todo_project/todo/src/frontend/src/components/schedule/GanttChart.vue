<template>
  <v-row>
    <v-col cols="12" md="2">
      <v-card>
        <v-treeview selectable :items="filter"></v-treeview>
      </v-card>
    </v-col>
    <v-col cols="12" md="10">
      <div style="width:98%; height:700px;">
        <gantt-elastic :tasks="tasks" :options="options"></gantt-elastic>
      </div>
    </v-col>
  </v-row>
</template>

<script>
// import { mapState, mapActions } from "vuex";
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
        user:
          '<a href="https://www.google.com/search?q=John+Doe" target="_blank" style="color:#0077c0;">John Doe</a>',
        start: getDate(-24 * 5),
        duration: 15 * 24 * 60 * 60 * 1000,
        progress: 85,
        type: "project"
        //collapsed: true,
      },
      {
        id: 2,
        label: "With great power comes great responsibility",
        user:
          '<a href="https://www.google.com/search?q=Peter+Parker" target="_blank" style="color:#0077c0;">Peter Parker</a>',
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
        user:
          '<a href="https://www.google.com/search?q=Clark+Kent" target="_blank" style="color:#0077c0;">Clark Kent</a>',
        start: getDate(-24 * 2),
        duration: 2 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "task",
        dependentOn: [3]
      },
      {
        id: 4,
        label: "Put that toy AWAY!",
        user:
          '<a href="https://www.google.com/search?q=Clark+Kent" target="_blank" style="color:#0077c0;">Clark Kent</a>',
        start: getDate(-24 * 2),
        duration: 2 * 24 * 60 * 60 * 1000,
        progress: 50,
        type: "task",
        dependentOn: [3]
      },
      {
        id: 4,
        label: "Put that toy AWAY!",
        user:
          '<a href="https://www.google.com/search?q=Clark+Kent" target="_blank" style="color:#0077c0;">Clark Kent</a>',
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
    ];
    return {
      tasks: tasks,
      options: options,
      filter: filter
    };
  }
};
</script>

<style></style>
