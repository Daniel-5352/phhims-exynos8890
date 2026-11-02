//SPDX-License-Identifier: GPL-2.0
package me.phh.sip

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

object SipAudioTrackFactory {
    fun createVoiceCallTrack(
        audioCodec: NegotiatedAudioCodec = SipAudioCodecs.AMR_NB,
    ): AudioTrack {
        val rawMinBufferSize = AudioTrack.getMinBufferSize(
            audioCodec.sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
        )
        val minBufferSize = if (rawMinBufferSize > 0) rawMinBufferSize else (audioCodec.sampleRate * 2)

        return AudioTrack(
            AudioManager.STREAM_VOICE_CALL,
            audioCodec.sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            minBufferSize,
            AudioTrack.MODE_STREAM,
        )
    }
}
