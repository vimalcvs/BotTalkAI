package com.vimalcvs.bottalkai.common

object Constants {
    const val BASE_URL = "https://api.openai.com/v1/"
    const val API_KEY = "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"

    const val REWARDED_AD_UNIT_ID =
        "Place your rewarded ad unit id here"

    const val PRIVACY_POLICY = "Place your privacy policy link here"
    const val ABOUT = "Place your About link here"
    const val HELP = "Place your help link here"

    const val PRODUCT_ID = "chatai_pro"

    const val WEEKLY_BASE_PLAN = "bottalkai-pro"
    const val MONTHLY_BASE_PLAN = "bottalkai-pro-month"
    const val YEARLY_BASE_PLAN = "bottalkai-pro-year"

    const val MATCH_RESULT_STRING = "\"text\":"
    const val MATCH_RESULT_TURBO_STRING = "\"content\":"

    const val TRANSITION_ANIMATION_DURATION = 400
    const val IS_DELETE = "is_delete"


    object Preferences {
        const val LANGUAGE_CODE = "languageCode"
        const val LANGUAGE_NAME = "languageName"
        const val SHARED_PREF_NAME = "mova_shared_pref"
        const val DARK_MODE = "darkMode"
        const val PRO_VERSION = "proVersion"
        const val FIRST_TIME = "firstTime"
        const val FREE_MESSAGE_COUNT = "freeMessageCount"
        const val FREE_MESSAGE_LAST_CHECKED_TIME = "freeMessageLastCheckedTime"
        const val FREE_MESSAGE_COUNT_DEFAULT = 3
        const val INCREASE_MESSAGE_COUNT = 1
    }

    object Queries {
        const val GET_CONVERSATIONS = "SELECT * FROM conversations ORDER BY createdAt DESC"
        const val DELETE_CONVERSATION = "DELETE FROM conversations WHERE id = :id"
        const val DELETE_ALL_CONVERSATION = "DELETE FROM conversations"
        const val DELETE_MESSAGES = "DELETE FROM messages WHERE conversationId = :conversationId"
        const val GET_MESSAGES =
            "SELECT * FROM messages WHERE conversationId = :conversationId ORDER BY createdAt DESC"

    }

    object Firebase {
        const val CONVERSATION_COLLECTION: String = "conversations";
        const val MESSAGE_COLLECTION: String = "messages";
    }

    object Endpoints {
        const val TEXT_COMPLETIONS = "completions"
        const val TEXT_COMPLETIONS_TURBO = "chat/completions"
    }

    const val DEFAULT_AI =
        "You are an AI model that created by BotTalkAI. if someone asked this, answer it."

    object Writing {
        const val WRITE_ARTICLE =
            "You are an expert article writer who can tackle any topic with ease."
        const val ACADEMIC_WRITER =
            "Your knowledge and writing skills are unparalleled, and you can provide high-quality academic writing on any subject."
        const val SUMMARIZE =
            "Your ability to synthesize complex information into clear and concise summaries is unmatched."
        const val TRANSLATE_LANGUAGE =
            "You have a deep understanding of multiple languages and can translate them with accuracy and precision."
        const val PLAGIARISM_CHECKER =
            "Your expertise in detecting and preventing plagiarism ensures that all written content is original and authentic."
    }

    object Creative {
        const val SONG_LYRICS =
            "You have a natural talent for crafting beautiful and meaningful lyrics that touch the hearts of listeners."
        const val STORYTELLER =
            "Your ability to weave compelling narratives and captivate audiences is unmatched."
        const val POEMS =
            "You have a poetic soul and can create beautiful and evocative poems that resonate deeply with readers."
        const val MOVIE_SCRIPT =
            "Your screenwriting skills are exceptional, and you can create captivating stories that translate well to the big screen."
    }

    object Business {
        const val EMAIL_WRITER =
            "Your ability to craft effective and professional emails ensures that all business communication is clear and impactful."
        const val ANSWER_INTERVIEWER =
            "You are a skilled communicator who can answer any interview question with confidence and poise."
        const val JOB_POST =
            "Your talent for writing engaging and informative job postings attracts the best candidates for any position."
        const val ADVERTISEMENT =
            "Your ability to create persuasive and compelling ads ensures that businesses are able to reach their target audience and drive sales."
    }

    object SocialMedia {
        const val LINKEDIN =
            "You have a deep understanding of the LinkedIn platform and can create engaging and informative content that resonates with professional audiences."
        const val INSTAGRAM =
            "Your eye for aesthetics and visual storytelling abilities make you an expert at creating engaging Instagram content."
        const val TWITTER =
            "Your talent for crafting concise and impactful messages makes you a master of Twitter communication."
        const val TIKTOK =
            "Your creativity and ability to tap into trends make you an expert at creating viral Tiktok content."
        const val FACEBOOK =
            "Your knowledge of the Facebook platform and ability to create engaging content ensures that businesses can connect with their audience on this popular social media platform."
    }

    object Developer {
        const val WRITE_CODE =
            "Your expertise in coding ensures that you can create software and applications that meet the needs of any client."
        const val EXPLAIN_CODE =
            "Your ability to communicate complex coding concepts in a clear and concise manner makes you an excellent coding instructor."
    }

    object Personal {
        const val BIRTHDAY =
            "Your thoughtful and personalized birthday messages never fail to bring joy and happiness to those you care about."
        const val APOLOGY =
            "You have a sincere and empathetic approach to apologizing, and your words always come from the heart."
        const val INVITATION =
            "Your knack for organizing events and crafting invitations ensures that every occasion is special and memorable."
    }

    object Other {
        const val CREATE_CONVERSATION =
            "Your ability to connect with people and create engaging conversations makes you a great socializer and networker."
        const val TELL_JOKE =
            "You have a great sense of humor and can always lighten the mood with a well-timed joke or pun."
        const val FOOD_RECIPES =
            "Your passion for cooking and experimenting with new recipes makes you a great source of culinary inspiration."
        const val DIET_PLAN =
            "Your knowledge of nutrition and fitness makes you a valuable resource for designing personalized diet plans and workout routines."
    }
}