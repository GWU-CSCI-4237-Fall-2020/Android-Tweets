package edu.gwu.androidtweets

import org.json.JSONObject
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun testSearchTweetsJsonParsing() {
        val parser = SearchTweetsJsonParser()
        val json = JSONObject(exampleJson)
        val tweets = parser.parseJson(json)

        assertEquals(3, tweets.size)
        assertEquals("@LewisElrick Apple or Android? Cuz it ain’t working for me", tweets[0].content)
    }

    val exampleJson = """
        {
            "statuses": [
                {
                    "created_at": "Sat Nov 28 02:02:57 +0000 2020",
                    "id": 1332505224817340417,
                    "id_str": "1332505224817340417",
                    "text": "@LewisElrick Apple or Android? Cuz it ain’t working for me",
                    "truncated": false,
                    "entities": {
                        "hashtags": [],
                        "symbols": [],
                        "user_mentions": [
                            {
                                "screen_name": "LewisElrick",
                                "name": "Lewis gamez",
                                "id": 899309935447740416,
                                "id_str": "899309935447740416",
                                "indices": [
                                    0,
                                    12
                                ]
                            }
                        ],
                        "urls": []
                    },
                    "metadata": {
                        "iso_language_code": "en",
                        "result_type": "recent"
                    },
                    "source": "<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone</a>",
                    "in_reply_to_status_id": 1332504639548366848,
                    "in_reply_to_status_id_str": "1332504639548366848",
                    "in_reply_to_user_id": 899309935447740416,
                    "in_reply_to_user_id_str": "899309935447740416",
                    "in_reply_to_screen_name": "LewisElrick",
                    "user": {
                        "id": 2423757171,
                        "id_str": "2423757171",
                        "name": "☃️❄️Jolly Basics❄️🎅🏼🍥",
                        "screen_name": "WWE_CardBasics",
                        "location": "Gibbstown, NJ",
                        "description": "I’m 20. Played @WWESuperCard since 8/14/14 (Release Date) as WWE_CardBasics, BioMech++ tier, WWE fan🤙🏻Single🙃 I have two puppies Cash(Bubby) and Daisy❤️",
                        "url": null,
                        "entities": {
                            "description": {
                                "urls": []
                            }
                        },
                        "protected": false,
                        "followers_count": 686,
                        "friends_count": 660,
                        "listed_count": 1,
                        "created_at": "Thu Mar 20 00:44:08 +0000 2014",
                        "favourites_count": 1932,
                        "utc_offset": null,
                        "time_zone": null,
                        "geo_enabled": true,
                        "verified": false,
                        "statuses_count": 12358,
                        "lang": null,
                        "contributors_enabled": false,
                        "is_translator": false,
                        "is_translation_enabled": false,
                        "profile_background_color": "C0DEED",
                        "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png",
                        "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png",
                        "profile_background_tile": false,
                        "profile_image_url": "http://pbs.twimg.com/profile_images/1329205598190268418/yXTvYRFK_normal.jpg",
                        "profile_image_url_https": "https://pbs.twimg.com/profile_images/1329205598190268418/yXTvYRFK_normal.jpg",
                        "profile_banner_url": "https://pbs.twimg.com/profile_banners/2423757171/1605736154",
                        "profile_link_color": "1DA1F2",
                        "profile_sidebar_border_color": "C0DEED",
                        "profile_sidebar_fill_color": "DDEEF6",
                        "profile_text_color": "333333",
                        "profile_use_background_image": true,
                        "has_extended_profile": true,
                        "default_profile": true,
                        "default_profile_image": false,
                        "following": null,
                        "follow_request_sent": null,
                        "notifications": null,
                        "translator_type": "none"
                    },
                    "geo": null,
                    "coordinates": null,
                    "place": null,
                    "contributors": null,
                    "is_quote_status": false,
                    "retweet_count": 0,
                    "favorite_count": 0,
                    "favorited": false,
                    "retweeted": false,
                    "lang": "en"
                },
                {
                    "created_at": "Sat Nov 28 01:49:07 +0000 2020",
                    "id": 1332501747399614465,
                    "id_str": "1332501747399614465",
                    "text": "@yessmorgann I’m ctfu because my uncle has an Android &amp; he got Apple Music😭 i be confused",
                    "truncated": false,
                    "entities": {
                        "hashtags": [],
                        "symbols": [],
                        "user_mentions": [
                            {
                                "screen_name": "yessmorgann",
                                "name": "morgann/moniya",
                                "id": 804110980649250816,
                                "id_str": "804110980649250816",
                                "indices": [
                                    0,
                                    12
                                ]
                            }
                        ],
                        "urls": []
                    },
                    "metadata": {
                        "iso_language_code": "en",
                        "result_type": "recent"
                    },
                    "source": "<a href=\"http://twitter.com/download/iphone\" rel=\"nofollow\">Twitter for iPhone</a>",
                    "in_reply_to_status_id": 1332501499415572485,
                    "in_reply_to_status_id_str": "1332501499415572485",
                    "in_reply_to_user_id": 804110980649250816,
                    "in_reply_to_user_id_str": "804110980649250816",
                    "in_reply_to_screen_name": "yessmorgann",
                    "user": {
                        "id": 234528015,
                        "id_str": "234528015",
                        "name": "Ginger McKenna",
                        "screen_name": "___MIZANI",
                        "location": "Philadelphia, PA",
                        "description": "",
                        "url": null,
                        "entities": {
                            "description": {
                                "urls": []
                            }
                        },
                        "protected": false,
                        "followers_count": 1431,
                        "friends_count": 617,
                        "listed_count": 3,
                        "created_at": "Wed Jan 05 21:53:13 +0000 2011",
                        "favourites_count": 140,
                        "utc_offset": null,
                        "time_zone": null,
                        "geo_enabled": true,
                        "verified": false,
                        "statuses_count": 76776,
                        "lang": null,
                        "contributors_enabled": false,
                        "is_translator": false,
                        "is_translation_enabled": false,
                        "profile_background_color": "C0DEED",
                        "profile_background_image_url": "http://abs.twimg.com/images/themes/theme1/bg.png",
                        "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme1/bg.png",
                        "profile_background_tile": true,
                        "profile_image_url": "http://pbs.twimg.com/profile_images/1220715506744938496/ztAK8SzB_normal.jpg",
                        "profile_image_url_https": "https://pbs.twimg.com/profile_images/1220715506744938496/ztAK8SzB_normal.jpg",
                        "profile_banner_url": "https://pbs.twimg.com/profile_banners/234528015/1603740831",
                        "profile_link_color": "F58EA8",
                        "profile_sidebar_border_color": "FFFFFF",
                        "profile_sidebar_fill_color": "ABD6D2",
                        "profile_text_color": "E3294E",
                        "profile_use_background_image": true,
                        "has_extended_profile": true,
                        "default_profile": false,
                        "default_profile_image": false,
                        "following": null,
                        "follow_request_sent": null,
                        "notifications": null,
                        "translator_type": "none"
                    },
                    "geo": null,
                    "coordinates": null,
                    "place": null,
                    "contributors": null,
                    "is_quote_status": false,
                    "retweet_count": 0,
                    "favorite_count": 0,
                    "favorited": false,
                    "retweeted": false,
                    "lang": "en"
                },
                {
                    "created_at": "Sat Nov 28 01:30:02 +0000 2020",
                    "id": 1332496944820871172,
                    "id_str": "1332496944820871172",
                    "text": "@edbott That's what happens when you remove the HUNDREDS OF MILLIONS of data points that were Android users.",
                    "truncated": false,
                    "entities": {
                        "hashtags": [],
                        "symbols": [],
                        "user_mentions": [
                            {
                                "screen_name": "edbott",
                                "name": "Ed Bott",
                                "id": 12199652,
                                "id_str": "12199652",
                                "indices": [
                                    0,
                                    7
                                ]
                            }
                        ],
                        "urls": []
                    },
                    "metadata": {
                        "iso_language_code": "en",
                        "result_type": "recent"
                    },
                    "source": "<a href=\"http://twitter.com/download/android\" rel=\"nofollow\">Twitter for Android</a>",
                    "in_reply_to_status_id": 1332493966814576640,
                    "in_reply_to_status_id_str": "1332493966814576640",
                    "in_reply_to_user_id": 12199652,
                    "in_reply_to_user_id_str": "12199652",
                    "in_reply_to_screen_name": "edbott",
                    "user": {
                        "id": 19883262,
                        "id_str": "19883262",
                        "name": "Andrew Rackow",
                        "screen_name": "TheRackow",
                        "location": "Cheltenham, PA",
                        "description": "Computer scientist, apparent web developer, tech enthusiast, gamer, computer builder… if it has a circuit I love it. He/him",
                        "url": "https://t.co/6fVSo3TbRo",
                        "entities": {
                            "url": {
                                "urls": [
                                    {
                                        "url": "https://t.co/6fVSo3TbRo",
                                        "expanded_url": "http://www.andrewrackow.com",
                                        "display_url": "andrewrackow.com",
                                        "indices": [
                                            0,
                                            23
                                        ]
                                    }
                                ]
                            },
                            "description": {
                                "urls": []
                            }
                        },
                        "protected": false,
                        "followers_count": 187,
                        "friends_count": 417,
                        "listed_count": 9,
                        "created_at": "Sun Feb 01 23:29:41 +0000 2009",
                        "favourites_count": 776,
                        "utc_offset": null,
                        "time_zone": null,
                        "geo_enabled": true,
                        "verified": false,
                        "statuses_count": 15877,
                        "lang": null,
                        "contributors_enabled": false,
                        "is_translator": false,
                        "is_translation_enabled": false,
                        "profile_background_color": "352726",
                        "profile_background_image_url": "http://abs.twimg.com/images/themes/theme5/bg.gif",
                        "profile_background_image_url_https": "https://abs.twimg.com/images/themes/theme5/bg.gif",
                        "profile_background_tile": false,
                        "profile_image_url": "http://pbs.twimg.com/profile_images/1859478346/IMG_1135_normal.jpg",
                        "profile_image_url_https": "https://pbs.twimg.com/profile_images/1859478346/IMG_1135_normal.jpg",
                        "profile_banner_url": "https://pbs.twimg.com/profile_banners/19883262/1401889222",
                        "profile_link_color": "D02B55",
                        "profile_sidebar_border_color": "829D5E",
                        "profile_sidebar_fill_color": "99CC33",
                        "profile_text_color": "3E4415",
                        "profile_use_background_image": true,
                        "has_extended_profile": true,
                        "default_profile": false,
                        "default_profile_image": false,
                        "following": null,
                        "follow_request_sent": null,
                        "notifications": null,
                        "translator_type": "none"
                    },
                    "geo": null,
                    "coordinates": null,
                    "place": null,
                    "contributors": null,
                    "is_quote_status": false,
                    "retweet_count": 0,
                    "favorite_count": 1,
                    "favorited": false,
                    "retweeted": false,
                    "lang": "en"
                }
            ],
            "search_metadata": {
                "completed_in": 0.079,
                "max_id": 1332505224817340417,
                "max_id_str": "1332505224817340417",
                "next_results": "?max_id=1332384398679945216&q=android&geocode=39.953021%2C-75.15556%2C30mi&include_entities=1",
                "query": "android",
                "refresh_url": "?since_id=1332505224817340417&q=android&geocode=39.953021%2C-75.15556%2C30mi&include_entities=1",
                "count": 15,
                "since_id": 0,
                "since_id_str": "0"
            }
        }
    """.trimIndent()
}