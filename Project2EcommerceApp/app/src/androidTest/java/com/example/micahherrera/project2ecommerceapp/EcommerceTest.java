package com.example.micahherrera.project2ecommerceapp;

import android.support.test.rule.ActivityTestRule;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;


/**
 * Created by micahherrera on 7/1/16.
 */
public class EcommerceTest {

    public static Matcher<View> nthChildOf(final Matcher<View> parentMatcher, final int childPosition) {
        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("with " + childPosition + " child view of type parentMatcher");
            }

            @Override
            public boolean matchesSafely(View view) {
                if (!(view.getParent() instanceof ViewGroup)) {
                    return parentMatcher.matches(view.getParent());
                }

                ViewGroup group = (ViewGroup) view.getParent();
                return parentMatcher.matches(view.getParent()) && group.getChildAt(childPosition).equals(view);
            }
        };

    }

    @Rule
    public ActivityTestRule<MainActivity> mMainActivity =
            new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void search(){
        onView(withId(R.id.search)).perform(click());
        onView(withId(R.id.search_src_text)).perform(typeText("Andrew"))
                .perform(pressKey(KeyEvent.KEYCODE_ENTER));
        onView(nthChildOf(withId(R.id.recyclerProductList), 0))
                .check(matches(hasDescendant(withText("Andrew Bird"))));
    }

    @Test
    public void hasProducts(){
        onView(withId(R.id.recyclerProductList)).check(matches(hasDescendant(isDisplayed())));
    }

    @Test
    public void goesToDetail(){
        onView(nthChildOf(withId(R.id.recyclerProductList), 0))
                .perform(click());
        onView(withId(R.id.albumAlbumDetailView)).check(matches(withText("1999")));
    }
}
