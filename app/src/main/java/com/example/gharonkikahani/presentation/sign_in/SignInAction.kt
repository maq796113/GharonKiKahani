sealed interface SignInAction {
    data object OnTogglePasswordVisibility: SignInAction
    data object OnSimpleLoginClick: SignInAction

    data object OnGoogleLoginClick: SignInAction

    data object OnFacebookLoginClick: SignInAction

    data object OnSignUpClick: SignInAction



}