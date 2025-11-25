import { defineConfig, mergeConfig } from 'vitest/config'

export default defineConfig({
  test: {
    coverage: {
      reporter: ['html', 'lcov']
    },
    reporters: [
      'default',
      'junit'
    ],
    environment: 'jsdom'
  }
})
