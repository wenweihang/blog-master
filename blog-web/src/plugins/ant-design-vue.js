import Vue from 'vue';
import {
  Button,
  Layout,
  Input,
  Icon,
  Tag,
  Pagination,
  Skeleton,
  FormModel,
  Comment,
  message
} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';

Vue.use(Button)
Vue.use(Layout)
Vue.use(Input)
Vue.use(Icon)
Vue.use(Tag)
Vue.use(Pagination)
Vue.use(Skeleton)
Vue.use(FormModel)
Vue.use(Comment)
Vue.prototype.$message = message
