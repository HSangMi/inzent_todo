<template>
  <v-card class="mr-4 super-task-card" width="300">
    <v-card-title class="task-list-header">
      <v-icon v-if="!list.superTask.usePublic">mdi-lock</v-icon>
      {{ list.superTask.title }}
      <v-spacer></v-spacer>
      <v-btn icon @click="show = !show">
        <v-icon>{{ show ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>mdi-dots-vertical</v-icon>
      </v-btn>
    </v-card-title>
    <v-expand-transition>
      <div v-show="show">
        <v-divider></v-divider>
        <v-card-text class="expand-card-text">
          <v-row align="center" class="mx-0">
            <!-- <v-rating :value="4.5" color="amber" dense half-increments readonly size="14"></v-rating> -->
            <div
              class="grey--text"
            >{{ list.superTask.memberNo }} RegDate:{{ list.superTask.regDate }}</div>
          </v-row>

          <!-- <div class="my-4 subtitle-1">$ • Italian, Cafe</div> -->

          <div class="my-4">{{ list.superTask.description }}</div>
          <div class="my-2" v-if="list.superTask.startDate||list.superTask.endDate">
            <v-chip label small color="#cacaca">
              <v-icon left>mdi-clock-outline</v-icon>
              {{list.superTask.startDate}} - {{ list.superTask.endDate}}
            </v-chip>
          </div>
          <div v-if="list.superTask.labels">
            <v-chip
              v-for="label in setLabel(list.superTask.labels)"
              :key="label.lableNo"
              filter
              label
              small
              dark
              class="mr-1"
              :color="label.labelColor"
            >{{label.labelName}}</v-chip>
          </div>
        </v-card-text>
      </div>
    </v-expand-transition>
    <v-divider></v-divider>
    <v-card-text class="list-content px-0 py-2">
      <ul class="task-list">
        <draggable v-model="items" v-bind="dragOptions" class="list-group">
          <transition-group type="transition" :name="!drag ? 'flip-list' : null">
            <task-child
              v-for="item in items"
              :item="item"
              :list="list"
              :board="board"
              :key="item.taskId"
            />
          </transition-group>
        </draggable>
      </ul>
    </v-card-text>
    <!-- <v-divider></v-divider> -->
    <v-card-actions class="pa-0">
      <v-btn text block @click.prevent="showAddTask(list.superTask.taskId, lastSubTaskPos)">
        <v-icon>mdi-plus</v-icon>Add Sub Tasks
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import Draggable from "vuedraggable";
import { mapMutations, mapState, mapActions } from "vuex";

export default {
  props: ["board", "list"],
  data() {
    return {
      selection: 0,
      show: false,
      drag: false,
      active: false
    };
  },
  components: {
    Draggable,
    TaskChild: () => import("./TaskChild.vue")
  },
  computed: {
    ...mapState({
      labelList: "labelList"
    }),
    dragOptions() {
      return {
        animation: "200",
        ghostClass: "ghost",
        group: "kanban-board-list-items"
      };
    },
    lastSubTaskPos() {
      const lastSubTask = this.list.subTaskList[
        this.list.subTaskList.length - 1
      ];
      let sortNo = 65535;
      if (lastSubTask) sortNo = lastSubTask.sortNo + sortNo;
      return sortNo;
    },
    items: {
      get() {
        console.log("get items..");
        // console.log(this.list.subTaskList);
        return this.list.subTaskList;
      },
      set(reorderedListItems) {
        console.log("set items..");
        console.log(reorderedListItems);

        const payload = {
          boardId: this.board.id,
          memberNo: this.board.memberNo,
          taskSuperId: this.list.superTask.taskId,
          items: reorderedListItems
        };
        // for (var i in reorderedListItems) {
        //   payload.items.push({
        //     taskSuperId: reorderedListItems[i].taskSuperId,
        //     taskId: reorderedListItems[i].taskId,
        //     sortNo: reorderedListItems[i].sortNo
        //   });
        // }
        this.reorderTaskListItems(payload);
      }
    }
  },
  methods: {
    ...mapMutations([
      "SET_ADD_TASK_MODAL",
      "SET_SUPER_TASK_ID",
      "SET_LAST_SUB_SORT_NO"
    ]),
    ...mapActions(["REORDER_TASK"]),
    showAddSubTask(taskId, sortNo) {
      console.log(sortNo);
      this.SET_ADD_SUB_TASK_MODAL(true);
      this.SET_SUPER_TASK_ID(taskId);
      this.SET_LAST_SUB_SORT_NO(sortNo);
    },
    reorderTaskListItems(payload) {
      console.log("----reorderTaskItems-----");
      // console.log("this.list", this.list);
      var targetTask = {};
      var items = payload.items;
      // console.log("items", items);
      // console.log("--------------------------");
      targetTask.taskSuperId = payload.taskSuperId;
      targetTask.projectId = payload.boardId; // 재호출용 데이터
      targetTask.memberNo = payload.memberNo; // 재호출용 데이터

      if (this.list.subTaskList.length === 0) {
        console.log("업무대 빵");
        console.log(items);
        targetTask.taskId = items[0].taskId;
        targetTask.sortNo = 65535;
        return this.REORDER_TASK(targetTask);
      }
      for (var i = 0; i < items.length; i++) {
        // console.log(i, "items[i]", item[i].sortNo);
        if (i == 0 && items[i].sortNo >= items[i + 1].sortNo) {
          console.log("카드가 맨~~위로 올라온경우", i);
          targetTask.taskId = items[i].taskId;
          targetTask.sortNo = items[i + 1].sortNo / 2;
          return this.REORDER_TASK(targetTask);
        } else if (
          i > 0 &&
          i < items.length - 1 &&
          items[i].sortNo <= items[i - 1].sortNo &&
          items[i].sortNo < items[i + 1].sortNo
        ) {
          console.log("카드가 중간에서 이동한 경우!", i);
          targetTask.taskId = items[i].taskId;
          targetTask.sortNo = (items[i - 1].sortNo + items[i + 1].sortNo) / 2;
          return this.REORDER_TASK(targetTask);
        } else if (
          i == items.length - 1 &&
          items[i].sortNo < items[i - 1].sortNo
        ) {
          console.log("카드가 맨~~아래로 내려온경우", i);
          targetTask.taskId = items[i].taskId;
          targetTask.sortNo = items[i - 1].sortNo + 65535;
          return this.REORDER_TASK(targetTask);
        } else if (items[i].taskSuperId != payload.taskSuperId) {
          console.log("다른리스트로 옮겼지만 순서엔 문제없음");
          // console.log("item의 슈퍼id: ", items[i].taskSuperId);
          // console.log("현재 리스트 id: ", payload.taskSuperId);
          targetTask.taskId = items[i].taskId;
          targetTask.taskSuperId = payload.taskSuperId;
          return this.REORDER_TASK(targetTask);
        }
      }
      // this.list.subTaskList = payload.items;
      // console.log("////reorderTaskItems-----");
      // this.REORDER_TASK(targetTask);
    },
    // getLabelNo(labelString) {
    //   // console.log(labelString);
    //   // console.log(this.list.superTask);
    //   if (labelString !== null) {
    //     var labelsNo = labelString.split(":");
    //     const labels = [];
    //     labelsNo = labelsNo.slice(0, labelsNo.length - 1);
    //     for (var no in labelsNo) {
    //       var lb = this.labelList.find(item => {
    //         return item.labelNo == labelsNo[no];
    //       });
    //       labels.push(lb);
    //     }
    //     // console.log("---label no---");
    //     return labels;
    //   }
    // },
    showAddTask(taskSuperId, sortNo) {
      console.log("SUB TASK ADD..");
      console.log(sortNo);
      // this.SET_ADD_TASK_MODAL(true);
      this.SET_ADD_TASK_MODAL(true);
      this.SET_SUPER_TASK_ID(taskSuperId);
      this.SET_LAST_SUB_SORT_NO(sortNo);
      this.SET_LAST_SUB_SORT_NO(sortNo);
    },
    setLabel(labelString) {
      const labelArr = JSON.parse(labelString); // labelArr Json String으로 변환할것 -> DB에 저장하는값
      var taskLabel = []; // 라벨로 보여줄 객체뽑아올곳
      for (var i in labelArr) {
        var lb = this.labelList.find(item => {
          return item.labelNo == labelArr[i];
        });
        taskLabel.push(lb);
        //this.taskLabel[i] = this.labelList[labelArr[i]];
        //this.labels += this.taskLabel[i].labelNo + ":";
      }
      return taskLabel;
    }
  }
};
</script>

<style scoped>
ul,
li {
  list-style: none;
  padding: 0;
}
.task-list {
  padding: 0px;
}
.super-task-card {
  background-color: #dcdcdc;
}
.super-task-card .v-card__title {
  font-size: 1.2em;
  font-weight: bold;
  color: #9e9e9e;
  /* background-color: #685083; */
  padding: 5px;
}
.list-group {
  min-height: 20px;
  display: flex;
  flex-direction: column !important;
}
.expand-card-text {
  background-color: #dcdcdc;
}
</style>
