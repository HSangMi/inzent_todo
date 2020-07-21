<template>
  <v-dialog
    persistent
    max-width="600px"
    v-model="isDetailSub"
    :close-on-content-click="false"
    offset-x
  >
    <v-card>
      <v-card-title class="blue-grey lighten-4 pa-5">
        <span class="headline px-5">
          <v-icon>mdi-clipboard-text-outline</v-icon>
          {{getSuperTitle}}
        </span>
      </v-card-title>
      <v-card-text>
        <v-container>
          <template v-if="getsubList">
            <!-- <v-list
              subheader
              three-line
              v-for="item in clickDateList"
              :key="item.ctitle"
              @click="true"
            >-->
            <v-list three-line>
              <v-list-item-group multiple active-class="grey lighten-3">
                <template v-for="(item, index) in getsubList">
                  <v-list-item :key="item.cId">
                    <v-list-item-content>
                      <v-list-item-title class="text-h5" v-text="item.ctitle"></v-list-item-title>
                      <v-spacer></v-spacer>
                      <v-list-item-subtitle v-if="item.imgCode!=null">
                        <v-tooltip
                          bottom
                          color="#5a5a5a"
                          v-for="(mem, index) in item.managerName"
                          :key="index"
                        >
                          <template v-slot:activator="{ on, attrs }">
                            <v-avatar v-bind="attrs" v-on="on" size="20px">
                              <img class="user-avatar" :src="item.imgCode[index]" />
                            </v-avatar>
                          </template>
                          <span font-color="white" class="user-font">{{mem}}</span>
                        </v-tooltip>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.imgCode==null">
                        <span class="user-font my-5">No Member</span>
                      </v-list-item-subtitle>
                      <v-spacer></v-spacer>
                      <v-list-item-subtitle v-if="item.cstartDate != '' && item.cendDate != ''">
                        <span class="detail-font">{{item.cstartDate}} ~ {{item.cendDate}}</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.cstartDate == '' && item.cendDate !=''">
                        <span class="detail-font">{{item.cstartDate}} ~ 미정</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.cstartDate != '' && item.cendDate == ''">
                        <span class="detail-font">미정 ~ {{item.cendDate}}</span>
                      </v-list-item-subtitle>
                      <v-list-item-subtitle v-if="item.cstartDate == '' && item.cendDate == ''">
                        <span class="detail-font">기간 미정</span>
                      </v-list-item-subtitle>
                    </v-list-item-content>
                    <v-list-item-action>
                      <v-chip
                        v-if="item.cstate == 'P' "
                        class="text-center ma-2 my-5"
                        x-small
                        color="blue"
                        text-color="white"
                      >진행</v-chip>
                      <v-chip
                        v-if="item.cstate == 'W'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="yellow"
                      >대기</v-chip>
                      <v-chip v-if="item.cstate == 'H' " class="text-center ma-2" x-small>보류</v-chip>
                      <v-chip
                        v-if="item.cstate == 'E'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="red"
                        text-color="white"
                      >긴급</v-chip>
                      <v-chip
                        v-if="item.cstate == 'C'"
                        class="text-center ma-2 my-5"
                        x-small
                        color="green"
                        text-color="white"
                      >완료</v-chip>
                    </v-list-item-action>
                    <v-list-item-action>
                      <v-icon
                        class="my-5"
                        small
                        v-show="item.cusePublic"
                      >mdi-lock-open-variant-outline</v-icon>
                      <v-icon class="my-5" small v-show="!item.cusePublic">mdi-lock-outline</v-icon>
                    </v-list-item-action>
                  </v-list-item>
                  <v-divider v-if="index + 1 < getsubList.length" :key="index"></v-divider>
                </template>
              </v-list-item-group>
            </v-list>
            <!-- <v-list-item-avatar></v-list-item-avatar> -->
          </template>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="blue darken-1" text @click="SET_IS_DETAIL_SUB(false)">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapMutations, mapState } from "vuex";

export default {
  data: () => ({
    subList: ""
  }),
  created() {},
  computed: {
    ...mapState({
      getsubList: "getsubList",
      getSuperTitle: "getSuperTitle"
    }),
    ...mapState(["isDetailSub"])
  },
  methods: {
    ...mapMutations(["SET_IS_DETAIL_SUB"])
  }
};
</script>

<style scoped>
.detail-font {
  font-size: 11px;
  color: cadetblue;
}
.user-font {
  font-size: 11px;
}
.user-list {
  padding-left: 15px;
}
.user-avatar {
  margin-left: -5px;
}
</style>
