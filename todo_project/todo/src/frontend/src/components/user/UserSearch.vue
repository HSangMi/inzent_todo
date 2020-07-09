<template>
  <v-dialog v-model="openModal" persistent max-width="1300">
    <v-card class="mx-auto" max-width="1300">
      <v-container fluid>
        <v-row>
          <v-col cols="3">
            <v-treeview
              v-model="selection"
              :items="items"
              selectable
              selection-type="leaf"
              return-object
              open-all
              :open.sync="open"
              open-on-click
              @input="selectDept"
            ></v-treeview>
          </v-col>
          <v-divider vertical></v-divider>
          <v-col>
            <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Search"
              single-line
              hide-details
            ></v-text-field>
            <v-data-table
              :headers="headers"
              :items="this.userList"
              :search="search"
              v-model="selected"
              show-select
              >
              <template v-slot:item.avatar="{ item }">
                <v-avatar size="32px" v-if="item.imgCode">
                  <!-- <img :src="'data:image;base64,'+item.imgCode"> -->
                  <img :src="getImgCode(item)">
                </v-avatar>
                <v-avatar size="32px" v-else>
                  <v-icon>mdi-account</v-icon>
                </v-avatar>
              </template> 
            </v-data-table>      
          </v-col>
          <v-divider vertical></v-divider>
          <v-col class="pa-6" cols="2">
            <template v-if="!selected.length">
              No one selected.
            </template>
            <v-sheet class="pa-4">
              <v-chip-group 
              column
              multiple            
              v-model="selected"
              >
                <v-chip v-for="mem in selected"
                :key="mem.id"
                class="ma-2"
                close
                @click="remove(mem)"
                @click:close="remove(mem)">
                  <v-avatar v-if="mem.imgCode"> 
                    <img :src="getImgCode(mem)">
                  </v-avatar>
                  <v-avatar v-else>
                    <v-icon>mdi-account</v-icon>
                  </v-avatar>
                  {{ mem.name }}
                </v-chip>
              </v-chip-group>
            </v-sheet>
          </v-col>          
        </v-row>
        <v-row>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text >확인</v-btn>
          <v-btn color="blue darken-1" text @click="onClose">취소</v-btn>
        </v-row>
        
      </v-container>
    </v-card>
  </v-dialog>
</template>

<script>
import { mapState, mapActions } from 'vuex';
export default {
  props: ["openModal"],
  data: () => ({
    dialog: false,
    selection: [],
    items: [],
    search: '',
    selected: [],
    headers: [
      {
        text: '사진',
        align: 'start',
        sortable: false,
        value: 'avatar',
      },
      { text: '이름', value: 'name' },
      { text: '부서', value: 'deptName' },
      { text: '직급', value: 'rank' },
      { text: '연락처', value: 'phone' },
      { text: '이메일', value: 'email' },
    ],
    model: null, 
    myTree: '',
  }),
  created() {
    
    this.fatch_data()
  },
  computed: {
    ...mapState({
      deptList: "deptList",
      userList: "userList"
    })
  },
  methods: {
    ...mapActions(["FATCH_DEPTLIST", "FATCH_USERLIST"]),

    fatch_data() {
      this.FATCH_DEPTLIST().then(() => {
        let list = this.deptList

        console.log("list", list)
        list.forEach(item => {
          for(let i=0; i<list.length; i++) {
            if(item.parentDeptCode ===list[i].deptCode) {
                item.parentId = list[i].id
                break;
            }
          }
        })
        console.log("foreach list", list)

        let myTree = null
        list.forEach(item => {
          if(myTree === null) {
            myTree={id: item.id, name: item.deptName}
            console.log(';',myTree)
          } else {
            this.Recursive(item, myTree)
          }
        })
        // console.log("myTree", myTree)

        let treeArr = [];
        treeArr.push(myTree);

        this.items = treeArr;
        this.myTree = myTree;
      });
    },

    Recursive(item,tree) {
      //tree Id 와 item parentId가 같다면 
      if(tree.id === item.parentId) {
        if(typeof tree.children === "undefined") { // tree에 children이 없다면
          tree.children = []
            tree.children.push({id : item.id, name : item.deptName})
        }else { // tree에 children이 있다.
          tree.children.push({id : item.id, name : item.deptName})
        }
      }else { //tree Id와 item parentId가 다름
        if(typeof tree.children === "undefined") { // tree에 children이 없다면
          return ;
        }else { // tree에 children이 있다면
           tree.children.forEach(child => {
             this.Recursive(item,child)
           })
        }
      }
    },

    onClose() {
      this.$emit("close")
    },
    remove (item) {
      console.log(item)
      const index = this.selected.indexOf(item);
      console.log("index: " + index);
      if (index >= 0) this.selected.splice(index, 1);
    },

    selectDept(){
      const selectedDept = []
      for(let i=0; i<this.selection.length; i++) {
        var dptmt = this.deptList.find(item =>{
          return item.id == this.selection[i].id
        })
        selectedDept.push(dptmt.deptCode)
      }

      console.log("myTree", this.myTree)
      console.log("chlidren id " + this.myTree.children[0].id)
      console.log(selectedDept)

      this.FATCH_USERLIST(selectedDept)
    },
    getImgCode(item) {
      return 'data:image;base64,' + item.imgCode
    },
  }

}
</script>

<style>
.v-treeview-node__root {
  min-height: 35px;
}
</style>