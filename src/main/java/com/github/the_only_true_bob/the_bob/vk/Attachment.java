package com.github.the_only_true_bob.the_bob.vk;

import java.util.Optional;

public interface Attachment {
    AttachmentType type();
    Optional<String> audioArtist();
    Optional<String> text();

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

            @Override
            public Optional<String> text() {
                return Optional.empty();
            }
        };
    }

    static Builder builder() {
        return new Builder();
    }

    class Builder {
        private AttachmentType type;
        private String audioArtist;
        private String text;

        public Builder setType(AttachmentType type) {
            this.type = type;
            return this;
        }

        public Builder setAudioArtist(String audioArtist) {
            this.audioArtist = audioArtist;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
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

                @Override
                public Optional<String> text() {
                    return Optional.ofNullable(text);
                }
            };
        }
    }
}
