package com.github.the_only_true_bob.the_bob.vk;

import java.util.Optional;

public interface Attachment {
    static Builder builder() {
        return new Builder();
    }

    AttachmentType type();

    Optional<String> audioArtist();

    static Attachment empty() {
        return new Attachment() {
            @Override
            public AttachmentType type() {
                return AttachmentType.UNASSIGNED;
            }

            @Override
            public Optional<String> audioArtist() {
                return Optional.empty();
            }
        };
    }

    class Builder {
        private AttachmentType type;
        private String audioArtist;

        public Builder setType(AttachmentType type) {
            this.type = type;
            return this;
        }

        public Builder setAudioArtist(String audioArtist) {
            this.audioArtist = audioArtist;
            return this;
        }

        public Attachment build() {
            return new Attachment() {
                @Override
                public AttachmentType type() {
                    return type;
                }

                @Override
                public Optional<String> audioArtist() {
                    return Optional.ofNullable(audioArtist);
                }
            };
        }
    }
}
