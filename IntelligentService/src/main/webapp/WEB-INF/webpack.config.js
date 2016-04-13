var webpack = require('webpack');
var path = require('path');
var ExtractTextPlugin = require("extract-text-webpack-plugin");
var HtmlWebpackPlugin = require('html-webpack-plugin');

var config = {
  context: path.join(__dirname, '..', 'WEB-INF'),
  entry: {
      // Add each page's entry here
      homepage: './pages/resources/js/homepage', //the mapping jsx file
      logon: './pages/resources/js/logon'

    },
    output: {
      path: path.join(__dirname, '..', '/WEB-INF/build'),
      filename: '[name].bundle.js'
    },
    plugins: [
      new webpack.DefinePlugin({
        __DEV__: JSON.stringify(JSON.parse(process.env.BUILD_DEV || 'true')), // judge if dev environment.
        __PRERELEASE__: JSON.stringify(JSON.parse(process.env.BUILD_PRERELEASE || 'false')) // judge if secret environment.
      }),
      new ExtractTextPlugin("[name].css"),
      new HtmlWebpackPlugin({
        template: './pages/templates/homepage.html', //file name in template directoty
        filename: 'homepage.html',                   //the generated file name
        chunks: ['homepage'],                        // match the entry name
        inject: 'body'                               //the js file inject to
      }),
      new HtmlWebpackPlugin({
        template: './pages/templates/logon.html',
        filename: 'logon.html',
        chunks: ['logon'],
        inject: 'body'
      })
    ],
    module: {
      perLoaders: [
        {
          test: /\.(js|jsx)?$/,
          exclude: /node_modules/,
          loader: 'jshint-loader'
        }
      ],
      loaders: [
        {
           test: /\.jsx?$/,
           exclude: /node_modules/,
           loader: "babel"
         },
         {
           test: /\.css$/,
           loader: ExtractTextPlugin.extract('style-loader', 'css-loader!autoprefixer?{browsers:["last 2 version", "> 1%"]}')
         },
         {
           test: /\.scss$/,
           loader: 'style-loader!css-loader!autoprefixer?{browsers:["last 2 version", "> 1%"]}!sass'
         },
         {
           test: /\.(jpe?g|png|gif)$/i,
           loader: 'url?limit=10000!img?progressive=true'
         },
         {
            test: /\.(eot|woff|ttf|svg)$/,
            loader: 'url?limit=10000'
          }
      ],
      noParse: []
    },
    resolve: {
      extensions: ['', '.js', '.json', '.jsx'],
      alias: {}
    },
    devtool: 'eval-source-map',
    jshint: {
      "esnext": true
    },
    devServer: {
      historyApiFallback: {
        index: 'logon.html',
        rewrites: [
          { from: /\/homepage/, to: '/homepage.html'}, //page router for browser
          { from: /\/logon/, to: '/logon.html'}
        ]
      },
      proxy: {
        '/api/*': {
          target: 'http://127.0.0.1:8080',
          secure: false
        }
      }
    }
};

module.exports = config;