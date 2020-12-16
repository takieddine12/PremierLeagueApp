package taki.eddine.premier.league.pro.uilisteners

import taki.eddine.premier.league.pro.models.NewsModel

interface RssListener {

     fun rssArticles(model : NewsModel)
}