
const webpack = require('webpack');

config.webpack.plugins.push(
    new webpack.ProvidePlugin({
        // alias these global variables to their appropriate modules in node
        // so that they don't have to be explicitly required in your code
        Vue: 'vue',
        VueCompilerDOM: '@vue/compiler-dom',
        VueServerRenderer: '@vue/server-renderer',
        mount: ['@vue/test-utils', 'mount'],
        shallowMount: ['@vue/test-utils', 'shallowMount'],
    })
)

