/* * SPDX-License-Identifier: AGPL-3.0-or-later * * C Copyright 2023 Regione Piemonte * */
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CopyWebpackPlugin = require('copy-webpack-plugin');
const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const Path = require('path');

module.exports = (env, arg) => {
	console.log(arg.mode);
	console.log(arg.customcssfilename);
	return {
	entry: {
    main: './src/App.js',
    styleCustom: './src/custom_css/'+arg.customcssfilename+'.js',
  },
  output: {
    filename: '[name].[contenthash].js',
  	path: Path.resolve('../webapp'),
  },
  optimization: {
    runtimeChunk: 'single',
    moduleIds: 'hashed',
  },
  devServer: {
    port: 9000
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: 'babel-loader',
          options: {
            presets: [
              ["@babel/preset-env", {
                  "useBuiltIns": "usage",
                  "targets": "> 0.25%, not dead",
                  "corejs":3
              }]
            ],
            plugins: [
              "@babel/plugin-proposal-class-properties",
              "@babel/plugin-transform-runtime",
              "@babel/plugin-transform-classes"
            ]
        }
        }
      },
      {
        test: /\.html$/,
        use: [
          {
            loader: 'html-loader',
            options: { minimize: true }
          }
        ]
      },
      {
        type: 'javascript/auto',
         test: /\.json/,
         exclude: /(node_modules|bower_components)/,
         use: [{
           loader: 'file-loader',
           options: { name: '[name].[ext]' },
         }],
       },
      {
        test: /\.css$/,
        use: [MiniCssExtractPlugin.loader, 'css-loader']
      },
      {
    	    // Match woff2 in addition to patterns like .woff?v=1.1.1.
    	    test: /\.(woff|woff2)(\?v=\d+\.\d+\.\d+)?$/,
    	    use: {
    	      loader: 'url-loader',
    	      options: {
    	        // Limit at 20k. Above that it emits separate files
    	        limit: 20000,

    	        // url-loader sets mimetype if it's passed.
    	        // Without this it derives it from the file extension
    	        mimetype: 'application/font-woff',

    	        // Output below fonts directory
    	        name: 'fonts/[name]_[hash].[ext]'
    	    }
    	}
      }
    ]
  },
  plugins: [
	new CleanWebpackPlugin({cleanOnceBeforeBuildPatterns: ['*.*','./css']}),
	new CopyWebpackPlugin([
		 { from: './src/css',
         	to: 'css'}
    ]),
    new HtmlWebPackPlugin({
      template: './src/index.html',
      filename: './index.html',
    }),
    new MiniCssExtractPlugin({
      filename: '[name].[contenthash].css'
    })
  ]
}
};
