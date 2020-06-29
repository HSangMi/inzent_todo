<template>
    <div style="width:98%; height:700px; border:1px solid #CCD1D1">
      <gantt-elastic :tasks="tasks" :options="options">
      </gantt-elastic>
    </div> 
</template>

<script>
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
            label: "DATE",
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
    return {
      tasks: tasks,
      options: options
    };
  }
};
</script>

<style></style>
