package com.example.internproject.presentation.utils

import androidx.core.text.isDigitsOnly

class ValidateSignUp {

    fun doValidate(flag: SignUpField, content: String): ValidateResult {
        return when(flag) {
            SignUpField.NAME -> {
                validateName(content)
            }
            SignUpField.ID -> {
                validateId(content)
            }
            SignUpField.PWD -> {
                validatePwd(content)
            }
            else -> {
                throw Exception("An Invalid Flag to Validate")
            }
        }
    }

    private fun validateName(name: String): ValidateResult {
        return if(name.isEmpty()) {
            ValidateResult.NAME_EMPTY
        } else if(!Regex("^[가-힣]*\$").matches(name) || name.contains(" ")) {
            ValidateResult.NAME_INVALID
        } else {
            ValidateResult.APPROVED
        }
    }
    private fun validateId(id: String): ValidateResult {
        return  if(id.length < 6) {
            ValidateResult.ID_TOO_SHORT
        } else if(id.length > 12) {
            ValidateResult.ID_TOO_LONG
        } else if(!Regex("^[a-zA-Z0-9]+\$").matches(id)) {
            ValidateResult.ID_ONLY_ENGLISH_NUMBER
        } else if(id.isDigitsOnly()) {
            ValidateResult.ID_USE_ENGLISH
        } else {
            ValidateResult.APPROVED
        }
    }
    private fun validatePwd(pwd: String): ValidateResult {
        return  if(pwd.length < 6) {
            ValidateResult.PWD_TOO_SHORT
        } else if(pwd.length > 12) {
            ValidateResult.PWD_TOO_LONG
        } else if(
            !Regex("^(?=.*[A-Za-z])(?=.*\\\\d)[A-Za-z\\\\d]*\$").matches(pwd) &&
            !Regex("^(?=.*[A-Za-z])(?=.*[^A-Za-z\\\\d])[A-Za-z\\\\d[^A-Za-z\\\\d]]*\$").matches(pwd) &&
            !Regex("^(?=.*\\\\d)(?=.*[^A-Za-z\\\\d])[A-Za-z\\\\d[^A-Za-z\\\\d]]*\$").matches(pwd)
        ) {
            ValidateResult.PWD_CONTAIN_TWO_OPTION
        } else {
            ValidateResult.APPROVED
        }
    }

    enum class ValidateResult(val msg: String) {
        APPROVED("사용 가능합니다"),

        NAME_EMPTY("이름을 입력해주세요."),
        NAME_INVALID("유효하지 않은 이름입니다."),     // 공백, 정확한 글자 아님
        NAME_ONLY_KOREAN("한글만 입력 가능합니다."),

        ID_TOO_SHORT("6글자 이상 입력해주세요."),
        ID_TOO_LONG("12글자 이하로 입력해주세요."),
        ID_ONLY_ENGLISH_NUMBER("영어와 숫자만 입력 가능합니다."),
        ID_USE_ENGLISH("숫자만 이용할 수 없습니다."),

        PWD_TOO_SHORT("6글자 이상 입력해주세요."),
        PWD_TOO_LONG("12글자 이하로 입력해주세요."),
        PWD_CONTAIN_TWO_OPTION("영어, 숫자, 특수 문자 중 최소 2가지 이상 조합해야 합니다."),

        PWD_NOT_SAME("비밀번호가 서로 다릅니다.")
    }
}