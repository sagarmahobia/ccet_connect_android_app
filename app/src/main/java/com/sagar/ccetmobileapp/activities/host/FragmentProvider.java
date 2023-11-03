package com.sagar.ccetmobileapp.activities.host;

import com.sagar.ccetmobileapp.activities.host.account.login.LoginFragment;
import com.sagar.ccetmobileapp.activities.host.account.login.LoginFragmentModule;
import com.sagar.ccetmobileapp.activities.host.account.login.LoginFragmentScope;
import com.sagar.ccetmobileapp.activities.host.account.otp.OtpFragment;
import com.sagar.ccetmobileapp.activities.host.account.otp.OtpFragmentModule;
import com.sagar.ccetmobileapp.activities.host.account.otp.OtpFragmentScope;
import com.sagar.ccetmobileapp.activities.host.account.signup.SignUpFragment;
import com.sagar.ccetmobileapp.activities.host.account.signup.SignUpFragmentModule;
import com.sagar.ccetmobileapp.activities.host.account.signup.SignUpFragmentScope;
import com.sagar.ccetmobileapp.activities.host.assignments.AssignmentsFragment;
import com.sagar.ccetmobileapp.activities.host.assignments.AssignmentsFragmentModule;
import com.sagar.ccetmobileapp.activities.host.assignments.AssignmentsFragmentScope;
import com.sagar.ccetmobileapp.activities.host.home.HomeFragment;
import com.sagar.ccetmobileapp.activities.host.home.HomeFragmentModule;
import com.sagar.ccetmobileapp.activities.host.home.HomeFragmentScope;
import com.sagar.ccetmobileapp.activities.host.notices.NoticeFragmentScope;
import com.sagar.ccetmobileapp.activities.host.notices.NoticesFragment;
import com.sagar.ccetmobileapp.activities.host.notices.NoticesFragmentModule;
import com.sagar.ccetmobileapp.activities.host.syllabus.SyllabusFragment;
import com.sagar.ccetmobileapp.activities.host.syllabus.SyllabusFragmentModule;
import com.sagar.ccetmobileapp.activities.host.syllabus.SyllabusFragmentScope;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by SAGAR MAHOBIA on 22-Jan-19. at 21:59
 */
@Module
public abstract class FragmentProvider {

    //methods are not used but @ are read to generate dependency map

    @HomeFragmentScope
    @ContributesAndroidInjector(modules = HomeFragmentModule.class)
    abstract HomeFragment homeFragment();

    @AssignmentsFragmentScope
    @ContributesAndroidInjector(modules = AssignmentsFragmentModule.class)
    abstract AssignmentsFragment assignmentsFragment();

    @NoticeFragmentScope
    @ContributesAndroidInjector(modules = NoticesFragmentModule.class)
    abstract NoticesFragment noticesFragment();

    @LoginFragmentScope
    @ContributesAndroidInjector(modules = LoginFragmentModule.class)
    abstract LoginFragment loginFragment();

    @SignUpFragmentScope
    @ContributesAndroidInjector(modules = SignUpFragmentModule.class)
    abstract SignUpFragment signUpFragment();

    @OtpFragmentScope
    @ContributesAndroidInjector(modules = OtpFragmentModule.class)
    abstract OtpFragment otpFragment();

    @SyllabusFragmentScope
    @ContributesAndroidInjector(modules = SyllabusFragmentModule.class)
    abstract SyllabusFragment syllabusFragment();

}
