const webpack = require('webpack');

config.resolve.alias = {
//    'Vue': 'vue/dist/vue.esm-bundler.js',
    'vue': 'vue/dist/vue.esm-bundler.js',
//    'VueCompilerDOM': '@vue/compiler-dom',
//    'VueServerRenderer': '@vue/server-renderer'
}

config.plugins.push(
    new webpack.ProvidePlugin({
        // alias these global variables to their appropriate modules in node
        // so that they don't have to be explicitly required in your code
//        Vue: ['vue/dist/vue.esm-bundler.js', 'default'],
//        vue: ['vue/dist/vue.esm-bundler.js', 'default'],
//        VueCompilerDOM: '@vue/compiler-dom',
//        VueServerRenderer: '@vue/server-renderer',
        mount: ['@vue/test-utils', 'mount'],
        shallowMount: ['@vue/test-utils', 'shallowMount'],
    })
)

