const path = require('path');
const webpack = require('webpack');
const copyPlugin = require('copy-webpack-plugin');
const { parseHTML } = require('jquery');

module.exports = {
  entry: './src/index.js',
  output: {
    globalObject: 'this',
    filename: 'bundle.js',
    publicPath: './',
    path: path.resolve(__dirname, 'dist')
  },
  plugins: [
    new webpack.ProvidePlugin({
      $: 'jQuery',
      jQuery: 'jquery',
      process: 'process/browser',
    }),
    new copyPlugin({
      patterns: [
        {
          from: path.resolve(__dirname, 'src', 'index.html'),
          to: path.resolve(__dirname, 'dist')
        },
        {
          from: path.resolve(__dirname, 'public'),
          to: path.resolve(__dirname, 'dist', 'public')
        }
      ],
    }),
  ],
  resolve: {
    fallback: {
      "http": require.resolve("stream-http"),
      "url": require.resolve("url/"),
      "util": require.resolve("util/"),
      "path": require.resolve("path-browserify"),
      "stream": require.resolve("stream-browserify"),
      "buffer": require.resolve("buffer/"),
      "querystring": require.resolve("querystring-es3"),
      "crypto": require.resolve("crypto-browserify"),
      "zlib": require.resolve("browserify-zlib"),
      "os": require.resolve("os-browserify"),
      "assert": require.resolve("assert/"),
      "vm": require.resolve("vm-browserify"),
    },
    extensions: ['.js', '.jsx', '.json'],
    alias: {
      jquery: 'jquery/dist/jquery.min.js',
    }
  },
  module: {
    rules: [
      {
        test: /\.css$/i,
        use: ['style-loader', 'css-loader'],
      },
    ],
  },
  target: 'node',
  externals: {
    fs: require('fs'),
  }
};

