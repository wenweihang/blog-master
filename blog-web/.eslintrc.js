module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: 'babel-eslint'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'space-before-function-paren': 'off', // 函数定义时括号前面要不要有空格
    'eol-last': 'off', // 文件以单一的换行符结束
    'no-extra-semi': 'off', // 可以多余的冒号
    semi: 'off', // 语句可以不需要分号结尾
    eqeqeq: 'off', // 必须使用全等
    'one-var': 'off', // 连续声明
    'no-undef': 'off', // 可以 有未定义的变量
    'no-unused-vars': 'off', // 能有声明后未被使用的变量或参数
    singleQuote: 'off',
    'no-template-curly-in-string': 'off'
  }
}
