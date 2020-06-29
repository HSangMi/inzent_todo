<template>
<v-card>
  <v-treeview
    selectable
    :items="items"
  ></v-treeview>
</v-card>
</template>

<script>
  export default {
    data: () => ({
      items: [
        {
          id: 1,
          name: '프로젝트',
          children: [
            { id: 2, name: 'Calendar : app' },
            { id: 3, name: 'Chrome : app' },
            { id: 4, name: 'Webstorm : app' },
          ],
        },
        {
          id: 5,
          name: '담당자',
          children: [
            {
              id: 6,
              name: 'vuetify :',
              children: [
                {
                  id: 7,
                  name: 'src :',
                  children: [
                    { id: 8, name: 'index : ts' },
                    { id: 9, name: 'bootstrap : ts' },
                  ],
                },
              ],
            },
            {
              id: 10,
              name: 'material2 :',
              children: [
                {
                  id: 11,
                  name: 'src :',
                  children: [
                    { id: 12, name: 'v-btn : ts' },
                    { id: 13, name: 'v-card : ts' },
                    { id: 14, name: 'v-window : ts' },
                  ],
                },
              ],
            },
          ],
        },
        {
          id: 15,
          name: '공개여부',
          children: [
            { id: 16, name: '공개' },
            { id: 17, name: '개인용' },
          ],
        },
      ],
    }),
  }
</script>

<style>

</style>